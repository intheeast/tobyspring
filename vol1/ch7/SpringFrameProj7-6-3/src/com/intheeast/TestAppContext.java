package com.intheeast;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import com.intheeast.springframe.service.DummyMailSender;
import com.intheeast.springframe.service.UserService;
import com.intheeast.springframe.service.UserServiceTest.TestUserService;

@Configuration
public class TestAppContext {
	@Bean
	public UserService testUserService() {
		TestUserService userService = new TestUserService();		
		return userService;
	}
	
	@Bean
	public MailSender mailSender() {
		return new DummyMailSender();
	}
}
