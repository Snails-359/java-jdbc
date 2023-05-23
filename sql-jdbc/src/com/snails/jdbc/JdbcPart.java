package com.snails.jdbc;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

/**
 * @description: 实现 Java-JDBC，原生
 * @author: Snails
 * @create: 2023-05-23 11:54
 * @version: v1.0
 */
public class JdbcPart {
    public static void main(String[] args) {
        try {
            // 注册驱动
            DriverManager.registerDriver(new Driver());
            // 获取连接
            String url = "jdbc:mysql://localhost:3306/jdbc_db";
            String user = "root";
            String password = "root8Snails";
            Connection connection = DriverManager.getConnection(url, user, password);
            // 创建statement
            Statement statement = connection.createStatement();
            // 发送 SQL 语句
            String sql = "select * from t_user;";
            ResultSet resultSet = statement.executeQuery(sql);
            // 对结果集解析
            while (resultSet.next()) {
                Object id = resultSet.getObject("id");
                Object account = resultSet.getObject("account");
                Object nikename = resultSet.getObject("nikename");

                System.out.println(id + "--" + account + "--" + nikename);
            }
            // 释放资源
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
