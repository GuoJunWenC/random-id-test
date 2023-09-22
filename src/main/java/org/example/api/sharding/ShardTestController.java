package org.example.api.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/shardTest")
public class ShardTestController {
      /* @Autowired
    private DataSource dataSource;
  @SneakyThrows
    @RequestMapping("/terst")
    public void terst(){
        DataSource dataSource=ShardingConfiguration.getDataSource();
        String sql = "INSERT INTO `t_order`(`order_id`, `order_no`, `user_id`, `amount`, `create_time`, `update_time`) VALUES (?,?,?,?,?,?);";

        for (int i = 0; i < 100; i++) {
            Long sharding = TinyId.nextId("test");

            try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, sharding);
            preparedStatement.setString(2, i+"");
            preparedStatement.setInt(3, i);
            preparedStatement.setInt(4, i);
            preparedStatement.setObject(5, LocalDateTime.now());
            preparedStatement.setObject(6, LocalDateTime.now());

            preparedStatement.executeUpdate();
        }
        }
    }
    @SneakyThrows
    @RequestMapping("/terst2")
    public void terst2(){
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
}
