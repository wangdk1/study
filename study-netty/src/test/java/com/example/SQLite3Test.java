package com.example;

import org.junit.Test;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author: wangdk
 * @create: 2020-06-17 13:31
 * @description:
 **/
public class SQLite3Test {
    @Test
    public void test() {
        try {


            URL systemResource = ClassLoader.getSystemResource("config/testDB.db");

            Class.forName("org.sqlite.JDBC");
            String db = systemResource.getFile();
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("select * from user;"); //查询数据
            while (rs.next()) { //将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + " "); //列属性一
                System.out.println("age = " + rs.getString("password")); //列属性二
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
