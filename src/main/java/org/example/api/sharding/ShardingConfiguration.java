package org.example.api.sharding;


import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;

import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;


public class ShardingConfiguration{
  /*  private static Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("db0", dataSource1());
        dataSourceMap.put("db1", dataSource2());
        return dataSourceMap;
    }

    public static DataSource dataSource1() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://8.142.153.195:3306/db0?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }


    public static DataSource dataSource2() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://8.142.153.195:3306/db1?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    private static ShardingRuleConfiguration createShardingRuleConfiguration() {
        ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();
        configuration.getTables().add(getOrderTableRuleConfiguration());
        *//**
         * 设置数据库的分片规则
         * inline表示行表达式分片算法，它使用groovy的表达式，支持单分片键，比如 t_user_$->{uid%8} 表示t_user表根据u_id%8分成8张表
         *//*
        configuration.setDefaultDatabaseShardingStrategy(
                new StandardShardingStrategyConfiguration("order_id", "table_inline"));
        *//**
         * 设置表的分片规则
         *//*
        configuration.setDefaultTableShardingStrategy(new StandardShardingStrategyConfiguration("order_id", "order_inline"));
        Properties props = new Properties();
        props.setProperty("algorithm-expression", "db${order_id%2}"); //表示根据order_id取模得到目标表
        *//**
         * 定义具体的分片规则算法，用于提供分库分表的算法规则
         *//*
        configuration.getShardingAlgorithms().put("table_inline", new ShardingSphereAlgorithmConfiguration("INLINE", props));
        Properties properties = new Properties();
        properties.setProperty("algorithm-expression", "t_order_${order_id%3}");
        configuration.getShardingAlgorithms().put("order_inline", new ShardingSphereAlgorithmConfiguration("INLINE", properties));
*//*
        configuration.getKeyGenerators().put("snowflake", new ShardingSphereAlgorithmConfiguration("SNOWFLAKE", getProperties()));
*//*
        return configuration;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("worker-id", "123");
        return properties;
    }

    //创建订单表的分片规则
    private static ShardingTableRuleConfiguration getOrderTableRuleConfiguration() {
        ShardingTableRuleConfiguration tableRule = new ShardingTableRuleConfiguration("t_order", "db${0..1}.t_order_${0..2}");
*//*
        tableRule.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("order_id", "snowflake"));
*//*
        return tableRule;
    }

    public static DataSource getDataSource() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("sql-show", "true");
        return ShardingSphereDataSourceFactory.createDataSource(createDataSourceMap(), Collections.singleton(createShardingRuleConfiguration()),properties);
    }
*/
}
