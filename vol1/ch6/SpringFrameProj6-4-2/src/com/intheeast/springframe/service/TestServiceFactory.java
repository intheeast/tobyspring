package com.intheeast.springframe.service;

import javax.sql.DataSource;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
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
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
	
	@Bean
	public TransactionAdvice transactionAdvice() {
		TransactionAdvice transactionAdvice = new TransactionAdvice();
		transactionAdvice.setTransactionManager(transactionManager());
		return transactionAdvice;
	}

	@Bean
	public NameMatchMethodPointcut transactionPointcut() {
		NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
		nameMatchMethodPointcut.setMappedName("upgrade*");
		return nameMatchMethodPointcut;
	}

	@Bean
	public DefaultPointcutAdvisor transactionAdvisor() {
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
		defaultPointcutAdvisor.setAdvice(transactionAdvice());
		defaultPointcutAdvisor.setPointcut(transactionPointcut());
		return defaultPointcutAdvisor;
	}	

	// application components
	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		return userDaoJdbc;
	}
	
	@Bean
	public ProxyFactoryBean userService() {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(userServiceImpl());
		proxyFactoryBean.setInterceptorNames("transactionAdvisor");		
		return proxyFactoryBean;
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
	
	
}
