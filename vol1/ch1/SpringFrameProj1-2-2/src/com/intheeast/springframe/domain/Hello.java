package com.intheeast.springframe.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Hello {
	
	private Connection helloWorld() throws SQLException, ClassNotFoundException {
				
		// 클래스 로더 가져오기
	    ClassLoader classLoader = getClass().getClassLoader(); 
	    
	    // 클래스 로드
	    Class<?> clazz = classLoader.loadClass("com.mysql.cj.jdbc.Driver"); 
	    // 클래스가 로드되어 있으면 clazz에 로드된 클래스가 할당됩니다.
	    
	    Connection c = DriverManager.getConnection(
	    		"jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8", 
				"root",
				"1234");   
	    
	    return c;
		
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Hello hello = new Hello();
		Connection c = hello.helloWorld();
		
		System.out.println("hello world");
	}

}

