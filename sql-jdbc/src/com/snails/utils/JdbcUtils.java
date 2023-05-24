package com.snails.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @description: JDBC 工具类；内部包含一个连接池对象，并对外提供一个获取连接和回收连接的方法。
 * @author: Snails
 * @create: 2023-05-24 20:31
 * @version: v5.1
 */
public class JdbcUtils {
    // 连接池对象
    private static DataSource dataSource = null;

    // 初始化对象池
    static {
        // 读取配置文件
        Properties properties = new Properties();
        InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //  创建连接，工厂模式
        try {
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取连接
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 收回连接
     * @param connection 要回收的连接
     * @throws SQLException the sql exception
     */
    public static void freeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
