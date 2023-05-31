package com.snails.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 基于 JDBC 工具类封装 dao 层重复代码;封装两个方法：一个简化DQL(数据查询语言)，一个简化DML(数据操纵语言)
 * @author: Snails
 * @create: 2023-05-24 23:08
 * @version: v1.0
 */
public abstract class BaseDao {
    /**
     * 封装一个简化DML(数据操纵语言)方法
     * @param sql    带占位符的 SQL 语句
     * @param params 占位符的值；注意传入的占位符的值必须与 SQL 语句中的占位符的位置一一对应！
     * @return 返回执行影响的行数
     * @throws SQLException the sql exception
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        // 获取连接
        Connection connection = JdbcUtilsV2.getConnection();
        // 创建 statement,并接收 SQL 语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 占位符赋值
        for (int i = 1; i <= params.length; i++) {
            preparedStatement.setObject(i, params[i - 1]);
        }
        // 发送 SQL 语句
        int rows = preparedStatement.executeUpdate();
        // 结果集解析
        // 释放资源
        if (connection.getAutoCommit()) {
            // 没有开启事务，正常回收连接;如果为 false 由业务层处理
            JdbcUtilsV2.freeConnection();
        }
        return rows;
    }

    /**
     * 封装一个简化DQL(数据查询语言)方法
     * @param <T>    声明的返回值的类型
     * @param clazz  返回值的实体类集合的模板对象
     * @param sql    带占位符的 SQL 语句；要求列表或别名与实体类的属性名一致。eg: u_id as uId => uId
     * @param params 占位符的值；注意传入的占位符的值必须与 SQL 语句中的占位符的位置一一对应！
     * @return 查询的实体类集合
     * @throws SQLException              the sql exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws NoSuchFieldException      the no such field exception
     * @throws NoSuchMethodException     the no such method exception
     * @throws InvocationTargetException the invocation target exception
     */
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... params) throws SQLException,
            InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException,
            InvocationTargetException {
        // 获取连接
        Connection connection = JdbcUtilsV2.getConnection();
        // 创建 statement,并接收 SQL 语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 占位符赋值
        if (params != null && params.length != 0) {
            for (int i = 1; i <= params.length; i++) {
                preparedStatement.setObject(i, params[i - 1]);
            }
        }
        // 发送 SQL 语句
        ResultSet resultSet = preparedStatement.executeQuery();
        // 结果集解析
        // --创建结果集接收对象集合
        List<T> list = new ArrayList<>();
        // --获取当前结果集对象列的信息
        ResultSetMetaData metaData = resultSet.getMetaData();
        // --获取当前列的长度，用作水平遍历列信息
        int columnCount = metaData.getColumnCount();
        // --遍历结果集
        while (resultSet.next()) {
            // 调用类的构造器实例化结果集对象。
            T t = clazz.getDeclaredConstructor().newInstance();
            // 遍历当前指定列
            for (int i = 1; i <= columnCount; i++) {
                // 获取指定列下标的列的名称
                String propertyName = metaData.getColumnLabel(i);
                // 获取指定列下标的列的属性值
                Object value = resultSet.getObject(i);

                // 反射，给对象的属性赋值
                Field field = clazz.getDeclaredField(propertyName);
                // 打破 private 修饰限制，属性可以设置
                field.setAccessible(true);
                field.set(t, value);
            }
            list.add(t);
        }
        // 回收连接
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()) {
            // 没有开启事务，正常回收连接;如果为 false 由业务层处理
            JdbcUtilsV2.freeConnection();
        }
        return list;
    }

}
