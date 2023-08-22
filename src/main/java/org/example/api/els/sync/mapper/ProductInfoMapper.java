package org.example.api.els.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.api.els.sync.entity.ProductInfo;

import java.util.List;

@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo>  {
    @Select(" select * from product_info ")
    List<ProductInfo> queryProductInfo();
}
