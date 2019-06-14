package com.test.service;

import java.sql.*;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
//        ServiceLoader<HelloService> services = ServiceLoader.load(HelloService.class);
//        services.forEach(service -> service.sayHello());

        /**
         * 研究源码发现，创建jdbc连接的第一步：Class.forname("com.mysql.jdbc.Driver") 非必须的
         * 只要classpath已经包含mysql-connector jar包即可，该步骤仅仅是为了验证有无该jar包
         * DriverManager.getConnection 会遍历classpath下面所有的jar包并初始化（SPI机制)
         */
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin?useSSL=false","root","root");
            Statement statement = connection.prepareStatement("select count(1) from articletag");
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
