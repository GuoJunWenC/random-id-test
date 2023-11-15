package org.example.api.requestmerge;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class QueryService {
    //用来存放请求的队列，我们将请求封装成了一个Request对象
    private final LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>() ;
    //这个是我们的单个的查询方法，假设每隔请求都根据唯一的code进行查询
    public Map<String,Object> query(String code){
        //这个request是我们自定义的内部类
        Request request = new Request();
        request.code = code;
        CompletableFuture<Map<String,Object>> future = new CompletableFuture<>();
        request.future = future;
        queue.add(request);
        //阻塞 直到返回结果
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    //这个是个模拟批量查询的方法
    public List<Map<String,Object>> batchQuery(List<String> codes){
        return null;
    }
    //封装的请求
    static class Request {
        String code;
        CompletableFuture<Map<String,Object>> future;
    }
    @PostConstruct
    public void init(){
        //在init方法中初始化一个定时任务线程，去定时执行我们的查询任务.具体的任务实现是我们根据唯一code查询出来的结果集，以code为key转成map，然后我们队列中的每个Request对象都有自己的唯一code，我们根据code一一对应，给相应的future返回对应的查询结果。
        ScheduledExecutorService poolExecutor = new ScheduledThreadPoolExecutor(1);
        poolExecutor.scheduleAtFixedRate(()->{
            int size = queue.size();
            //如果没有请求直接返回
            if(size==0)
                return ;
            List<Request> list = new ArrayList<>();
            for (int i = 0; i < size;i++){
                Request request = queue.poll();
                list.add(request);
            }
            System.out.println("批量处理:"+size);
            List<String> codes = list.stream().map(s->s.code).collect(Collectors.toList());
            //合并之后的结果集
            List<Map<String, Object>> batchResult = batchQuery(codes);
            Map<String,Map<String,Object>> responseMap = new HashMap<>();
            for (Map<String,Object> result : batchResult) {
                String code = result.get("code").toString();
                responseMap.put(code,result);
            }
            //返回对应的请求结果
            for (Request request : list) {
                Map<String, Object> response = responseMap.get(request.code);
                request.future.complete(response);
            }
        },0,10,TimeUnit.MILLISECONDS);
    }
}
