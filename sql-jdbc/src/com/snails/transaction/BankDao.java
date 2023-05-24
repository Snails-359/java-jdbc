package com.snails.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description: bank表数据库操作方法存储类
 * @author: Snails
 * @create: 2023-05-24 17:02
 * @version: v4.0
 */
public class BankDao {
    /**
     * 转入-加钱
     * @param account the account
     * @param money   the money
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sql exception
     */
    public void add(String account, int money, Connection connection) throws ClassNotFoundException, SQLException {
        // 注册驱动
        // 获取连接
        // 创建 SQL 语句
        String sql = "update t_bank set money = money + ? where account =?";
        // 创建 statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 占位符赋值
        preparedStatement.setObject(1, money);
        preparedStatement.setObject(2, account);
        // 发送 SQL 语句
        preparedStatement.executeUpdate();
        // 结果集解析

        // 释放资源
        preparedStatement.close();

        System.out.println("存入成功！");
    }

    /**
     * 转出-减钱
     * @param account the account
     * @param money   the money
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sql exception
     */
    public void sub(String account, int money, Connection connection) throws ClassNotFoundException, SQLException {
        // 注册驱动
        // 获取连接
        // 创建 SQL 语句
        String sql = "update t_bank set money = money - ? where account = ?";
        // 创建 statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 占位符赋值
        preparedStatement.setObject(1, money);
        preparedStatement.setObject(2, account);
        // 发送 SQL 语句
        preparedStatement.executeUpdate();
        // 结果集解析
        // 释放资源
        preparedStatement.close();

        System.out.println("转账成功！");
    }

}
