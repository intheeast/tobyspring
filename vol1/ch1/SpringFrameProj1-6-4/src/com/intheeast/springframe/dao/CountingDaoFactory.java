package com.intheeast.springframe.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}

	@Bean
	public ConnectionMaker realConnectionMaker() {
		DConnectionMaker maker =  new DConnectionMaker();
		maker.setDriverClass("com.mysql.cj.jdbc.Driver");
		maker.setUrl("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8");
		maker.setUsername("root");
		maker.setPassword("1234");
		return maker;
	}
}