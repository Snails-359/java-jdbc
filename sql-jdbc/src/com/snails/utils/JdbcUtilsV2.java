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
 * V2.0更新：利用线程本地变量存储连接信息，确保一个线程的多个方法获取同一个 connection 连接！
 * 优势：解决事务操作时 service 和 dao 同一个线程，传递参数问题
 * @author: Snails
 * @create: 2023-05-24 20:31
 * @version: v5.2
 */
public class JdbcUtilsV2 {
    // 连接池对象
    private static DataSource dataSource = null;
    // 线程本地变量存储连接池信息
    private static ThreadLocal<Connection> thread = new ThreadLocal<>();

    // 初始化对象池
    static {
        // 读取配置文件
        Properties properties = new Properties();
        InputStream inputStream = JdbcUtilsV2.class.getClassLoader().getResourceAsStream("druid.properties");
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
        // 获取线程本地变量中的 connection
        Connection connection = thread.get();

        if (connection ==null){
            // 获取连接
            connection = dataSource.getConnection();
            // 设置连接信息到线程本地变量中
            thread.set(connection);
        }
        // 返回连接
        return connection;
    }

    /**
     * 收回连接
     * @throws SQLException the sql exception
     */
    public static void freeConnection() throws SQLException {
        // 获取线程本地变量中的 connection
        Connection connection = thread.get();
        if (connection != null){
            // 清空线程本地变量数据
            thread.remove();
            // 事务状态回顾，开启事务自动提交
            connection.setAutoCommit(true);
            // 回收到连接池中
            connection.close();
        }
    }

}
