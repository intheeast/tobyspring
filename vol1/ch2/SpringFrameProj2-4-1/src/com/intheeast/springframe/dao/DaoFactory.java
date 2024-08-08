package com.intheeast.springframe.dao;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {
	
	@Bean
	public DataSource dataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		1. java.sql.Driver: 데이터베이스 드라이버를 나타내는 인터페이스입니다. 
						   //JDBC 드라이버는 이 인터페이스를 구현하여 드라이버를 등록하고, 데이터베이스와의 연결을 수립할 수 있습니다.
//
//		2. java.sql.Connection: 데이터베이스와의 연결을 나타내는 인터페이스입니다. 
								//드라이버는 이 인터페이스를 구현하여 Connection 객체를 생성하고, 데이터베이스와의 실제 연결을 수행합니다. Connection 객체는 Statement, PreparedStatement, CallableStatement 등을 생성하고, SQL 쿼리를 실행하며, 트랜잭션을 관리하는 등의 작업을 수행합니다.
//
//		3. java.sql.Statement: SQL 문을 실행하는 인터페이스입니다. 
								// Statement 객체를 생성하여 데이터베이스에 SQL 쿼리를 전달하고, 실행 결과를 받아올 수 있습니다.
//
//		4. java.sql.PreparedStatement: 미리 컴파일된 SQL 문을 실행하는 인터페이스입니다. 
								// PreparedStatement 객체를 생성하여 SQL 문을 미리 컴파일하고, 파라미터를 바인딩한 후에 실행할 수 있습니다. 이를 통해 성능 향상과 SQL Injection 공격에 대한 보안 강화를 할 수 있습니다.
//
//		5. java.sql.ResultSet: SQL 문의 실행 결과를 나타내는 인터페이스입니다. 
								// Statement나 PreparedStatement 객체를 통해 SQL 쿼리를 실행하고, ResultSet 객체를 통해 결과 집합을 받아올 수 있습니다. ResultSet을 통해 결과 데이터를 탐색하고 처리할 수 있습니다.
//
//		이 외에도 java.sql.DriverManager, java.sql.DatabaseMetaData 등의 인터페이스도 JDBC 드라이버에서 구현해야 할 수 있습니다. 이들 인터페이스들은 JDBC API의 핵심이
//		
		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		
		return dataSource;
	}

	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}

}
