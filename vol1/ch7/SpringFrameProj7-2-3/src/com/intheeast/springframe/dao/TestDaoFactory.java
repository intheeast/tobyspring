package com.intheeast.springframe.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.intheeast.springframe.sqlservice.XmlSqlService;

@Configuration
public class TestDaoFactory {
	
	@Bean
	public DataSource dataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		return dataSource;
	}
	
	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		userDaoJdbc.setSqlService(sqlService());
		return userDaoJdbc;
	}
	
	@Bean
	public XmlSqlService sqlService() {
		XmlSqlService xmlSqlService = new XmlSqlService();
		xmlSqlService.setSqlmapFile("sqlmap.xml");
		return xmlSqlService;
	}
}


