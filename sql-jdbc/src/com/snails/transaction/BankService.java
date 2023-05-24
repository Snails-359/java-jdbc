package com.snails.transaction;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description: 业务方法，调用 dao 方法
 * @author: Snails
 * @create: 2023-05-24 17:07
 * @version: v1.0
 */
public class BankService {

    @Test
    public void start() throws SQLException, ClassNotFoundException {
        transfer("xiaow", "xiaol", 500);
    }

    public void transfer(String addAccount, String subAccount, int money) throws SQLException, ClassNotFoundException {
        BankDao bankDao = new BankDao();

        // 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///jdbc_db", "root", "root8Snails");
        try {
            // 开启事务,关闭事务自动提交
            connection.setAutoCommit(false);
            // 执行数据库操作
            bankDao.add(addAccount, money, connection);
            System.out.println("--------------");
            bankDao.sub(subAccount, money, connection);
            // 提交事务
            connection.commit();
        } catch (Exception e) {
            // 事务回滚
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }
}
