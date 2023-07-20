package com.intheeast.springframe.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.intheeast.springframe.sqlservice.BaseSqlService;
import com.intheeast.springframe.sqlservice.DefaultSqlService;
import com.intheeast.springframe.sqlservice.HashMapSqlRegistry;
import com.intheeast.springframe.sqlservice.JaxbXmlSqlReader;
import com.intheeast.springframe.sqlservice.OxmSqlService;
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
	public OxmSqlService sqlService() {
		OxmSqlService oxmSqlService = new OxmSqlService();
		oxmSqlService.setUnmarshaller(unmarshaller());
		Resource sqlmap = new ClassPathResource(
				"/com/intheeast/springframe/dao/sqlmap.xml", 
				UserDao.class);
		oxmSqlService.setSqlmap(sqlmap);
		return oxmSqlService;
	}
	
	@Bean
	public Jaxb2Marshaller unmarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.intheeast.springframe.sqlservice.jaxb");
		return jaxb2Marshaller;
	}

}


