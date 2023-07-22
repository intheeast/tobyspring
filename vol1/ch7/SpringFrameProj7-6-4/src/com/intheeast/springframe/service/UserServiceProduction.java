package com.intheeast.springframe.service;

import static com.intheeast.springframe.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.intheeast.springframe.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.MailSender;

import com.intheeast.AppContext;
import com.intheeast.springframe.dao.UserDao;
import com.intheeast.springframe.domain.Level;
import com.intheeast.springframe.domain.User;

// ActiveProfiles is a class-level annotation that 
// is used to declare which active bean definition profiles should be used when loading an ApplicationContextfor test classes.
//@ActiveProfiles("production")
//<dependency>
//	<groupId>com.h2database</groupId>
//	<artifactId>h2</artifactId>
//	<version>2.1.214</version>
//	<scope>runtime</scope>
//</dependency>
public class UserServiceProduction {
	
	private static List<User> users;	// test fixture	
	
	public static void setUp() {	
		
		users = Arrays.asList(
				new User("bumjin", "박범진", "p1", "user1@ksug.org", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
				new User("joytouch", "강명성", "p2", "user2@ksug.org", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
				new User("erwins", "신승한", "p3", "user3@ksug.org", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
				new User("madnite1", "이상호", "p4", "user4@ksug.org", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
				new User("green", "오민규", "p5", "user5@ksug.org", Level.GOLD, 100, Integer.MAX_VALUE)
				);
	}	
	
	public static void main(String[] args) {

		setUp();
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("production");
		context.register(AppContext.class);
		context.refresh();		
		
		MailSender mailSender = context.getBean(MailSender.class);
		UserService userService = context.getBean("userService", UserService.class);	
		UserDao userDao = context.getBean("userDao", UserDao.class);
		
	}

}
