package com.snails.curd;

import org.junit.Test;

import java.sql.*;

/**
 * The type P sther part.
 * @description: 拓展 CURD 操作的的特殊功能或操作
 * @author: Snails
 * @create: 2023 -05-24 11:02
 * @version: v4.0
 */
public class PStherPart {
    /**
     * 主键回显；作用：在主从表插入数据时使用。
     */
    @Test
    public void returnPrimaryKey() {
        try {
            // 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql:///jdbc_db", "root", "root8Snails");
            // 创建 SQL 语句
            String sql = "insert into t_user(account,nikename,password) values(?,?,?);";
            // 创建 prepareStatement,预编译 SQL 语句；设置返回参数（主键）
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // 占位符赋值
            preparedStatement.setObject(1, "0088");
            preparedStatement.setObject(2, "驴蛋蛋");
            preparedStatement.setObject(3, "123456");
            // 发送 SQL 语句
            int rows = preparedStatement.executeUpdate();
            // 结果集解析
            if (rows > 0) {
                System.out.println("插入数据成功！");
                // 获取回显主键(固定形式：一行一列）
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                // 移动光标
                resultSet.next();
                int id = resultSet.getInt(1);
                System.out.println("id =" + id);
            } else {
                System.out.println("插入数据失败！！！");
            }
            // 释放资源
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert test.
     */
    @Test
    public void InsertTest() {
        try {
            // 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql:///jdbc_db", "root", "root8Snails");
            // 创建 SQL 语句
            String sql = "insert into t_user(account,nikename,password) values(?,?,?);";
            // 创建 Statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            long start = System.currentTimeMillis();

            for (int i = 0; i < 10000; i++) {
                // 占位符赋值
                preparedStatement.setObject(1, "bl" + i);
                preparedStatement.setObject(2, "tj" + i);
                preparedStatement.setObject(3, "ma" + i);
                // 发送 SQL 语句
                int rows = preparedStatement.executeUpdate();
            }

            long end = System.currentTimeMillis();
            // 执行耗时：5763
            System.out.println("执行耗时：" + (end - start));
            // 结果集解析
            // if (rows > 0) {
            //     System.out.println("批量插入数据成功！");
            // } else {
            //     System.out.println("批量插入数据失败！！！");
            // }
            // 释放资源
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Batch insert test.
     */
    @Test
    public void BatchInsertTest() {
        try {
            // 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接;开启批量添加
            Connection connection = DriverManager.getConnection("jdbc:mysql:///jdbc_db?rewriteBatchedStatements=true"
                    , "root", "root8Snails");
            // 创建 SQL 语句；注意 SQL 语句不能加分号
            String sql = "insert into t_user(account,nikename,password) values(?,?,?)";
            // 创建 statement
            PreparedStatement statement = connection.prepareStatement(sql);
            // 占位符赋值
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                statement.setObject(1, "bi" + i);
                statement.setObject(2, "bin" + i);
                statement.setObject(3, "bima" + i);
                // 不执行 SQL 语句，追加到 value 后面
                statement.addBatch();
            }
            // 发送 SQL语句
            int[] rows = statement.executeBatch();

            long end = System.currentTimeMillis();
            System.out.println("执行耗时：" + (end - start));
            // 结果集解析
            // 释放资源
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
