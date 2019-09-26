package com.example.demo.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component(value = "connectionSqlUtil")
public class ConnectionSqlUtil {

    public static Connection getConnectionIRR()
    {
        String driveName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://118.143.2.9:3306/sonicSMS?useUnicode=true&characterEncoding=UTF-8";

        String userName = "root";
        String password = "sonic@xunyu";
        Connection con = null;
        try {
            Class.forName(driveName);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, userName, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


}
