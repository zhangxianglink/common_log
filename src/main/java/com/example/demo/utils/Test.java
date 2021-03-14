package com.example.demo.utils;

import com.example.demo.utils.JdbcMysqlUtils;

import java.sql.*;

public class Test {
    public static void main(String[] args) throws Exception {
        test1();
        test1();
    }

    public static void test1() {
        System.out.println("------------");
        Connection conn = null;
        try {
            conn = JdbcMysqlUtils.getConn();
            PreparedStatement ps = conn.prepareStatement("select * from user");
            ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcMysqlUtils.close();
        }
    }
}
