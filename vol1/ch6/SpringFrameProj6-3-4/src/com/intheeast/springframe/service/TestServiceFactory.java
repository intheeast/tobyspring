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
	
	// UserService 인터페이스를 구현한 클래스 객체의 프록시[자바 다이나믹 프록시]를
	// 생성하는 팩토리빈을 Spring Ioc에 의해 생성되게 하고 등록
	// UserService 구체의 프록시도 빈으로 등록된다.
	// Bean Name : &userService
	// Bean Name : userService -> &userService[TxProxyFactoryBean]가 만든 프록시
	@Bean
	public TxProxyFactoryBean userService() {
		TxProxyFactoryBean txProxyFactoryBean = new TxProxyFactoryBean();
		txProxyFactoryBean.setTarget(userServiceImpl());
		txProxyFactoryBean.setTransactionManager(transactionManager());
		txProxyFactoryBean.setPattern("upgradeLevels");
		txProxyFactoryBean.setServiceInterface(UserService.class);
		return txProxyFactoryBean;
	}	
	
	@Bean
	public UserServiceImpl userServiceImpl() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		userServiceImpl.setUserDao(userDao());
		userServiceImpl.setMailSender(mailSender());
		return userServiceImpl;
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
