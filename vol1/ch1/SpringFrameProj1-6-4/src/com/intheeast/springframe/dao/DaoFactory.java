package com.intheeast.springframe.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		UserDao dao = new UserDao();
		dao.setConnectionMaker(connectionMaker());
		return dao;
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		DConnectionMaker maker = new DConnectionMaker();
		maker.setDriverClass("com.mysql.cj.jdbc.Driver");
		maker.setUrl("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8");
		maker.setUsername("root");
		maker.setPassword("1234");
		return maker;
	}

}
