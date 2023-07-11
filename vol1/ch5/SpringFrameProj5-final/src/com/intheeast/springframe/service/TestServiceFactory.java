package com.intheeast.springframe.service;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
		userService.setMailSender(mailSenderImpl()/*mailSender()*/);
		//userService.setDataSource(dataSource());
		return userService;
	}
	
	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender dummyMailSender = new DummyMailSender();
		//dummyMailSender.setJavaMailProperties(properites());		
		return dummyMailSender;
	}
	
	@Bean
	public JavaMailSenderImpl mailSenderImpl() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		
		mailSenderImpl.setJavaMailProperties(properites());		
		
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587); // TLS : 587, SSL : 465
		mailSenderImpl.setUsername("swseokitec@gmail.com"); 
		mailSenderImpl.setPassword("kmwmvsbajccozsxc");
		return mailSenderImpl;
	}
	
	@Bean
	public Properties properites() {
		Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return props;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
}
