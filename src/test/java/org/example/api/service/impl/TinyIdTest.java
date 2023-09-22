package org.example.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import lombok.SneakyThrows;
import org.example.api.els.sync.entity.Order;
import org.example.api.els.sync.mapper.OrderMapper;
import org.example.api.sharding.ShardingConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TinyIdTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void test() {
        /*    Long id = TinyId.nextId("test");
         */
// 根据业务类型 批量获取10个ID
        List<Long> ids = TinyId.nextId("test", 10);
        System.out.println(ids);
    }
   /* @Test
    @SneakyThrows
    public void testOrder2() {
        DataSource dataSource = new ShardingConfiguration().getShardingDataSource();
        String sql = "SELECT * FROM t_order o WHERE  o.order_id=?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, 10);
          try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getInt(2));
                }
            }
        }
    }*/
    @Test
    @SneakyThrows
    public void testOrder() {
        for (int i = 0; i <= 100; i++) {
            Long test = TinyId.nextId("test");
            System.out.println(test);
            int i1 = ThreadLocalRandom.current().nextInt(0, 2);
            orderMapper.insert(new Order(test+1, test + "", i1, i, LocalDateTime.now(), LocalDateTime.now()));
        }
    }
    @Test
    @SneakyThrows
    public void testOrderQuery() {
        List<Order> orders = orderMapper.selectList(Wrappers.lambdaQuery(Order.class).in(Order::getOrderNo,Lists.newArrayList("300005","12")));
        System.out.println(orders);
    }
}
