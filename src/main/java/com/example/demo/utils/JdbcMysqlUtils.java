package com.example.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcMysqlUtils {

    private static Connection conn = null;

    private JdbcMysqlUtils() {

    }


    public static Connection getConn() throws Exception {
        /**
         * Caused by: com.mysql.cj.exceptions.ConnectionIsClosedException: No operations allowed after connection closed.
         * 原因，单例模式下Connection 关闭, 但是对象本身还是存在的
         * 解决方案： 判断条件添加上 !conn.isClosed()
         */
        if (conn != null  && !conn.isClosed()) {
            return conn;
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://120.132.101.152:3306/scenter?useUnicode=true&characterEncoding=utf8",
                "root", "mysql+edata+1");
        return conn;
    }

    public static void close(){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
