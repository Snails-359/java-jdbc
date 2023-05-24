package com.snails.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @description: Druid 连接池的使用
 * @author: Snails
 * @create: 2023-05-24 19:51
 * @version: v5.0
 */
public class DruidPart {
    public void hardTest() throws SQLException {
        // 创建连接池对象
        DruidDataSource dataSource = new DruidDataSource();
        // 设置连接参数
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/jdbc_db");
        dataSource.setUsername("root");
        dataSource.setPassword("root8Snails");
        // 获取连接
        Connection connection = dataSource.getConnection();
        // CURD 操作
        // ...
        // 释放资源
        connection.close();
    }

    public void softTest() throws Exception {
        // 读取配置文件
        Properties properties = new Properties();
        InputStream inputStream = DruidPart.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(inputStream);
        // 创建连接池对象,工厂模式
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        // 获取连接
        Connection connection = dataSource.getConnection();
        // CURD 操作
        // ...
        // 释放资源
        connection.close();
    }
}
