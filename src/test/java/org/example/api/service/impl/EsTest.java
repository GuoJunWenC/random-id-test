package org.example.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.example.api.els.config.Animal;
import org.example.api.els.sync.entity.ProductInfo;
import org.example.api.els.sync.entity.ProductInfoVO;
import org.example.api.els.sync.mapper.ProductInfoMapper;
import org.example.api.els.sync.mapper.ProductInfoElasticsearchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EsTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
/*    @Autowired*/
    private ProductInfoElasticsearchRepository productInfoElasticsearchRepository;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    /**
     * 查询索引是否存在
     *
     */
    @Test
    public void queryIndexTest() {
        List<ProductInfo> productInfoList = productInfoMapper.queryProductInfo();
        for (ProductInfo productInfo : productInfoList) {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(productInfo,productInfoVO);
            productInfoElasticsearchRepository.save(productInfoVO);
        }

    }

    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void addIndexTest() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("product_info");
        //2.客户端执行请求IndicesClient，执行create方法创建索引，请求后获得响应
        CreateIndexResponse response =
                restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 删除索引
     *
     * @throws IOException
     */
    @Test
    public void deleteIndexTest() throws IOException {
        //1.删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("product_info");
        //执行delete方法删除指定索引
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    /**
     * 获取索引详情
     *
     * @throws IOException
     */
    @Test
    public void getIndexInfoTest() throws IOException {
        GetIndexRequest request = new GetIndexRequest("product_info");
        GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        System.out.println("mappings:" + JSON.toJSON(response.getMappings()));
        System.out.println("settings:" + response.getSettings());
    }

    /**
     * 创建文档
     *
     * @throws IOException
     */
    @Test
    public void createDocTest() throws IOException {
        IndexRequest request = new IndexRequest("animal");

        // 方式一：采用对象转Gson的方式创建文档
        Animal animal = new Animal("cat", "喵喵", 21, Lists.newArrayList("内卷", "吃饭"));
        //设置文档id=1，设置超时=5s等，不设置会使用默认的
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(5));

        request.source(JSON.toJSON(animal), Requests.INDEX_CONTENT_TYPE);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println("方式一：" + indexResponse.toString());
        System.out.println("方式一：" + indexResponse.status());
        System.out.println("=======================================");
        // 方式二：采用逐一传参的方式创建文档
        request.id("2");
        request.timeout(TimeValue.timeValueSeconds(5));
        request.source("dog", "汪汪", 110, Lists.newArrayList("会狗叫",
                "蹭人"));
        indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println("方式二：" + indexResponse.toString());
        System.out.println("方式二：" + indexResponse.status());
        System.out.println("=======================================");

        // 方式三：批量创建文档
        List<Animal> books = Lists.newArrayList(new Animal("duck", "嘎嘎", 22, Lists.newArrayList("内卷", "吃饭")),
                new Animal("chicken", "叽叽", 21, Lists.newArrayList("打篮球", "rap", "炒粉")),
                new Animal("rabbit", "兔兔", 234, Lists.newArrayList("内卷", "吃饭")));
        BulkRequest bulkRequest = new BulkRequest("animal");
        AtomicInteger id = new AtomicInteger(3);
        books.forEach(b -> bulkRequest.add(
                new IndexRequest("animal")
                        .id(String.valueOf(id.getAndIncrement())) // 指定文档id，不指定则取默认值
                        .source(JSON.toJSON(b), Requests.INDEX_CONTENT_TYPE)));

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("方式三：" + bulkResponse.toString());
        System.out.println("方式三" + bulkResponse.status());
    }

    /**
     * 获取文档数据
     *
     * @throws IOException
     */
    @Test
    public void getDocTest() throws IOException {
        //1.创建请求,指定索引、文档id
        GetRequest request = new GetRequest("product_info", "2");
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);

        System.out.println(getResponse);//获取响应结果
        //getResponse.getSource() 返回的是Map集合
        System.out.println(getResponse.getSourceAsString());//获取响应结果source中内容，转化为字符串
    }

    /**
     * 更新文档数据:
     *
     * @throws IOException
     */
    @Test
    public void updateDocTest() throws IOException {
        //1.创建请求,指定索引、文档id
        UpdateRequest request = new UpdateRequest("animal", "2");

        Animal animal = new Animal("mouse", "唧唧复唧唧", 1, Lists.newArrayList("内卷", "吃饭"));
        //将创建的对象放入文档中
        request.doc(JSON.toJSONString(animal), XContentType.JSON);

        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());//更新成功返回OK
    }

    /**
     * '
     * 删除文档
     *
     * @throws IOException
     */
    @Test
    public void deleteDocTest() throws IOException {
        //创建删除请求，指定要删除的索引与文档ID
        DeleteRequest request = new DeleteRequest("product_info", "1");

        DeleteResponse updateResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());//删除成功返回OK，没有找到返回NOT_FOUND
    }

    /**
     * 分页查询
     *
     * @throws IOException
     */
    @Test
    public void searchDocPageTest() throws IOException {
        SearchRequest request = new SearchRequest("animal");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.timeout(TimeValue.timeValueSeconds(10));
        builder.query(QueryBuilders.matchQuery("name", "mouse"));
        builder.from(0);
        builder.size(10);
        builder.highlighter(new HighlightBuilder().field("name").preTags("<muse>").postTags("</muse>")); // 高亮
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        Arrays.stream(response.getHits().getHits()).forEach(System.out::println);
    }

    /**
     * 包含以下操作：查询所有数据，分页，高亮，排序，模糊。
     *
     * @throws IOException
     */
    @Test
    public void allDocTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("animal");//里面可以放多个索引
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();//构造搜索条件

        //此处可以使用QueryBuilders工具类中的方法
        //1.查询所有
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //2.查询name中含有mouse的
        sourceBuilder.query(QueryBuilders.multiMatchQuery("dog", "name"));
        //3.分页查询
        sourceBuilder.from(0).size(2);

        //4.按照score正序排列
        //sourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.ASC));
        //5.按照id倒序排列（score会失效返回NaN）
        //sourceBuilder.sort(SortBuilders.fieldSort("_id").order(SortOrder.DESC));

        //6.给指定字段加上指定高亮样式
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").preTags("<span style='color:red;'>").postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //获取总条数
/*
        System.out.println(searchResponse.getHits().getTotalHits());
*/
        //输出结果数据（如果不设置返回条数，大于10条默认只返回10条）
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("分数:" + hit.getScore());
            Map<String, Object> source = hit.getSourceAsMap();
            System.out.println("index->" + hit.getIndex());
            System.out.println("id->" + hit.getId());
            for (Map.Entry<String, Object> s : source.entrySet()) {
                System.out.println(s.getKey() + "--" + s.getValue());
            }
        }
    }
}
