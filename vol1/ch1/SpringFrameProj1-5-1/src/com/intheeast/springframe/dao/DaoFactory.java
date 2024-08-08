package com.intheeast.springframe.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Annotation 기반의 Configuration Meta Data!!!
@Configuration  // 빈 객체간의 의존성을 주입...
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {		
		return new UserDao(connectionMaker());
	}	
	
	@Bean
	public ConnectionMaker connectionMaker() {		
		return new DConnectionMaker();
	}
}
