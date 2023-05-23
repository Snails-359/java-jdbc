package com.snails.jdbc;

import java.sql.*;
import java.util.Properties;

/**
 * @description: 实现 Java-JDBC，原生。更新：1.优化驱动注册，减少系统开销。2.优化Statement，避免 SQL 语句复杂度;使用prepareStatement()方法利用占位符减少
 * SQL语句中引号嵌套的复杂度;防止注入攻击问题。
 * @author: Snails
 * @create: 2023-05-23 11:54
 * @version: v2.0
 */
public class JdbcPartV2 {
    public static void main(String[] args) {
        try {
            // 注册驱动,利用反射避免驱动两次注册
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            String url = "jdbc:mysql://localhost:3306/student_db";

            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "root8Snails");

            Connection connection = DriverManager.getConnection(url, properties);
            // 创建 SQL 语句，并预编译;使用prepareStatement()方法利用占位符减少 SQL语句中引号嵌套的复杂度及注入攻击问题。
            String sql = "select * from students where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 给占位符赋值;注意传入的占位符的值必须与 SQL 语句中的占位符的位置一一对应
            preparedStatement.setObject(1, 1);
            // 发送 SQL 语句
            ResultSet resultSet = preparedStatement.executeQuery();
            // 对结果集解析
            while (resultSet.next()) {
                // 当前行，columnIdex 指行下标，columLabel指列名
                Object id = resultSet.getInt(1);
                Object name = resultSet.getString("name");
                Object gender = resultSet.getString("gender");
                Object age = resultSet.getString("age");

                System.out.println(id + "--" + name + "--" + gender + "--" + age);
            }
            // 释放资源
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
