package com.intheeast.springframe.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.intheeast.springframe.domain.User;


public class UserDaoTest {
	public static int ret() {
		return 1;
	}
	// JRE
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = 
				new AnnotationConfigApplicationContext(DaoFactory.class); // Spring IoC Container
																		  // BeanFactory + ApplicationContext
		
		System.out.println("hello world");
		
		int a = 3;
		int b = a;
		int c = ret();
		
		UserDao dao = context.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);
			
		System.out.println(user.getId() + "\n등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
			
		System.out.println(user2.getId() + "\n조회 성공");
	}

}
