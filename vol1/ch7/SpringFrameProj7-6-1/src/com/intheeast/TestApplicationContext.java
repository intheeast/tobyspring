package com.intheeast;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mail.MailSender;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.intheeast.springframe.dao.UserDao;
import com.intheeast.springframe.dao.UserDaoJdbc;
import com.intheeast.springframe.service.DummyMailSender;
import com.intheeast.springframe.service.UserService;
import com.intheeast.springframe.service.UserServiceImpl;
import com.intheeast.springframe.sqlservice.OxmSqlService;
import com.intheeast.springframe.sqlservice.SqlRegistry;
import com.intheeast.springframe.sqlservice.SqlService;
import com.intheeast.springframe.sqlservice.updatable.EmbeddedDbSqlRegistry;
import com.intheeast.springframe.service.UserServiceTest.TestUserService;
import com.mysql.cj.jdbc.Driver;

@Configuration  // java based configuration
@EnableTransactionManagement
public class TestApplicationContext {
	/**
	 * DB연결과 트랜잭션
	 */
	
	@Bean // java based configuration
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(Driver.class);
		ds.setUrl("jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8");
		ds.setUsername("root");
		ds.setPassword("1234");
		return ds;
	}
	
	@Bean // java based configuration
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	/**
	 * 애플리케이션 로직 & 테스트용 빈
	 */
	// annotation based configuration
	@Autowired SqlService sqlService;
	
	@Bean // java based configuration
	public UserDao userDao() {
		UserDaoJdbc dao = new UserDaoJdbc();
		dao.setDataSource(dataSource());
		dao.setSqlService(this.sqlService);
//		dao.setSqlService(sqlService());
		return dao;
	}
	
	@Bean // java based configuration
	public UserService userService() {
		UserServiceImpl service = new UserServiceImpl();
		service.setUserDao(userDao());
		service.setMailSender(mailSender());
		return service;
	}
	
	@Bean // java based configuration
	public UserService testUserService() {
		TestUserService testService = new TestUserService();
		testService.setUserDao(userDao());
		testService.setMailSender(mailSender());
		return testService;
	}
	
	@Bean // java based configuration
	public MailSender mailSender() {
		return new DummyMailSender();
	}
	
	/**
	 * SQL서비스
	 */
	
	@Bean // java based configuration
	public SqlService sqlService() {
		OxmSqlService sqlService = new OxmSqlService();
		sqlService.setUnmarshaller(unmarshaller());
		sqlService.setSqlRegistry(sqlRegistry());
		return sqlService;
	}
	
	@Bean // java based configuration
	public SqlRegistry sqlRegistry() {
		EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
		sqlRegistry.setDataSource(embeddedDatabase());
		return sqlRegistry;
	}
	
	@Bean // java based configuration
	public Unmarshaller unmarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.intheeast.springframe.sqlservice.jaxb");
		return marshaller;
	}
	
	@Bean // java based configuration
	public DataSource embeddedDatabase() {
		return new EmbeddedDatabaseBuilder()
				.setName("embeddedDatabase")
				.setType(EmbeddedDatabaseType.H2)
				.addScript("sqlRegistrySchema.sql")
				.build();
	}
}
