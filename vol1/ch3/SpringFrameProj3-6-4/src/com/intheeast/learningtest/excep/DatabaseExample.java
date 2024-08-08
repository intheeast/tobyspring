package com.intheeast.learningtest.excep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseExample {

	public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
            		"jdbc:mysql://localhost:3306/mydb", 
            		"username", 
            		"password");
            // 데이터베이스 작업 수행
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing the database connection: " + e.getMessage());
                }
            }
        }
    }

}
