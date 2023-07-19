package com.intheeast.springframe.service;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.intheeast.springframe.dao.UserDaoJdbc;
import com.intheeast.springframe.sqlservice.BaseSqlService;
import com.intheeast.springframe.sqlservice.HashMapSqlRegistry;
import com.intheeast.springframe.sqlservice.JaxbXmlSqlReader;
import com.intheeast.springframe.sqlservice.SimpleSqlService;
import com.intheeast.springframe.sqlservice.XmlSqlService;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "springframe")
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
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
	
	/*
	 <!-- sql service -->
	<bean id="sqlService" class="springbook.user.sqlservice.BaseSqlService">
		<property name="sqlReader" ref="sqlReader" />
		<property name="sqlRegistry" ref="sqlRegistry" />
	</bean>
	
	<bean id="sqlReader" class="springbook.user.sqlservice.JaxbXmlSqlReader">
		<property name="sqlmapFile" value="sqlmap.xml" />
	</bean>
	
	<bean id="sqlRegistry" class="springbook.user.sqlservice.HashMapSqlRegistry">
	</bean>
	 */
	
	@Bean
    public BaseSqlService sqlService() {
		BaseSqlService baseSqlService = new BaseSqlService();
		baseSqlService.setSqlReader(sqlReader());
		baseSqlService.setSqlRegistry(sqlRegistry());
        return baseSqlService;
    }
	
	@Bean
	public JaxbXmlSqlReader sqlReader() {
		JaxbXmlSqlReader jaxbXmlSqlReader = new JaxbXmlSqlReader();
		jaxbXmlSqlReader.setSqlmapFile("sqlmap.xml");
		return jaxbXmlSqlReader;
	}
	
	@Bean
	public HashMapSqlRegistry sqlRegistry() {
		HashMapSqlRegistry hashMapSqlRegistry = new HashMapSqlRegistry();
		return hashMapSqlRegistry;
	}

	// application components
	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		userDaoJdbc.setSqlService(sqlService());
		return userDaoJdbc;
	}	
	
	@Bean
	public UserServiceImpl userService() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		userServiceImpl.setUserDao(userDao());
		userServiceImpl.setMailSender(mailSender());		
		return userServiceImpl;
	}	
	
	@Bean
	public UserServiceImpl testUserService() {
	    UserServiceImpl testUserServiceImpl = new UserServiceTest.TestUserServiceImpl();
	    testUserServiceImpl.setUserDao(userDao());
	    testUserServiceImpl.setMailSender(mailSender());
	    return testUserServiceImpl;
	}
	
	@Bean
	public DummyMailSender mailSender() {
		DummyMailSender dummyMailSender = new DummyMailSender();
		return dummyMailSender;
	}	
}
