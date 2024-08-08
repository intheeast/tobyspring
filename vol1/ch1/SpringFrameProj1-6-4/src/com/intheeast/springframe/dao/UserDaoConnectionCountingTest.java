package com.intheeast.springframe.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.intheeast.springframe.domain.User;


public class UserDaoConnectionCountingTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = 
				new AnnotationConfigApplicationContext(CountingDaoFactory.class);		
		UserDao dao = context.getBean("userDao", UserDao.class);

		for(int i=0; i<10; i++) {
			User user = new User();
			user.setId(""+i);
			user.setName(""+i);
			user.setPassword(""+i);
			dao.add(user);
		}
	
		// connectionMaker(Spring Bean/UserDao의 add 메소드를 통해서)가 사용되었다는 것을 증명하는 코드
		CountingConnectionMaker ccm =  context.getBean("connectionMaker", CountingConnectionMaker.class);
		System.out.println("Connection counter : " + ccm.getCounter());		
	}

}
