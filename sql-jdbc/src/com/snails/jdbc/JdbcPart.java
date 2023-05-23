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
            String url = "jdbc:mysql://localhost:3306/student_db";
            String user = "root";
            String password = "root8Snails";
            Connection connection = DriverManager.getConnection(url, user, password);
            // 创建statement
            Statement statement = connection.createStatement();
            // 发送 SQL 语句
            String sql = "select * from students;";
            ResultSet resultSet = statement.executeQuery(sql);
            // 对结果集解析
            while (resultSet.next()) {
                Object id = resultSet.getObject("id");
                Object name = resultSet.getObject("name");
                Object gender = resultSet.getObject("gender");
                Object age = resultSet.getObject("age");

                System.out.println(id + "--" + name + "--" + gender + "--" + age);
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
