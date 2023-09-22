package org.example.api.els.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.api.els.sync.entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order>  {

}
