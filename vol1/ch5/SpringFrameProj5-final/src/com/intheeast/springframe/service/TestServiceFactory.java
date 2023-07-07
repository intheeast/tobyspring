package com.intheeast.springframe.service;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.intheeast.springframe.dao.UserDaoJdbc;

@Configuration
public class TestServiceFactory {
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
		return userDaoJdbc;
	}
	
	@Bean
	public UserService userService() {
		UserService userService = new UserService();
		userService.setUserDao(userDao());
		userService.setTransactionManager(transactionManager());
		//<property name="mailSender" ref="mailSender" />
		userService.setMailSender(mailSender());
		//userService.setDataSource(dataSource());
		return userService;
	}
	
	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender dummyMailSender = new DummyMailSender();
		return dummyMailSender;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
}
