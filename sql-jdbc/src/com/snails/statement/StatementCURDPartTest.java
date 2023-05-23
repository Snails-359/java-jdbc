package com.snails.statement;

import org.junit.Test;

import java.sql.*;
import java.util.*;

/**
 * @description: 使用preparedStatement()进行 CURD 操作
 * @author: Snails
 * @create: 2023-05-23 19:21
 * @version: v1.0
 */
public class StatementCURDPartTest {
    @Test
    public void InsertTest() {
        try {
            // 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            String url = "jdbc:mysql://localhost:3306/jdbc_db";
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "root8Snails");

            Connection connection = DriverManager.getConnection(url, properties);
            // 创建 SQL 语句，并预编译
            String sql = "insert into t_user values(?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setObject(1, null);
            preparedStatement.setObject(2, "小小");
            preparedStatement.setObject(3, "女");
            preparedStatement.setObject(4, "43");
            // 发送 SQL语句
            int rows = preparedStatement.executeUpdate();
            // 结果集解析
            if (rows > 0) {
                System.out.println("数据插入成功！");
            } else {
                System.out.println("数据插入失败！！！");
            }
            // 释放资源
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void UpdateTest() {
        try {
            // 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            String url = "jdbc:mysql://localhost:3306/jdbc_db";
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "root8Snails");

            Connection connection = DriverManager.getConnection(url, properties);
            // 创建 SQL语句，并预编译
            String sql = "update t_user set nikename = ? where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setObject(1, "小五");
            preparedStatement.setObject(2, 1);
            // 发送 SQL 语句
            int rows = preparedStatement.executeUpdate();
            // 结果集解析
            if (rows > 0) {
                System.out.println("数据更新成功！");
            } else {
                System.out.println("数据更新失败！！！");
            }
            // 释放资源
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void DeleteTest() {
        try {
            // 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            String url = "jdbc:mysql:///jdbc_db";
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "root8Snails");
            Connection connection = DriverManager.getConnection(url, properties);
            // 创建 SQL 语句，并预编译
            String sql = "delete from t_user where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setObject(1, 2);
            // 发送 SQL语句
            int rows = preparedStatement.executeUpdate();
            // 结果集解析
            if (rows > 0) {
                System.out.println("数据删除成功！");
            } else {
                System.out.println("数据删除失败！！！");
            }
            // 释放资源
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void SelectTest() {
        try {
            // 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql:///jdbc_db", "root", "root8Snails");
            // 创建 SQL语句，并预编译
            String sql = "select * from t_user;";
            // String sql = "select * from t_user where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            // preparedStatement.setObject(1,1);
            // 发送 SQL 语句
            ResultSet resultSet = preparedStatement.executeQuery();
            // 结果集解析
            List<Map> list = new ArrayList<>();
            while (resultSet.next()) {
                Map map = new HashMap();
                map.put("id", resultSet.getInt("id"));
                map.put("account", resultSet.getString("account"));
                map.put("nikename", resultSet.getString("nikename"));
                map.put("password", resultSet.getString("password"));
                list.add(map);
            }
            System.out.println("list" + list);
            // 释放资源
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
