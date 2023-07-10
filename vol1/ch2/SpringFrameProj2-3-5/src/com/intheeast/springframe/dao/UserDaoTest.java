package com.intheeast.springframe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.intheeast.springframe.domain.User;

public class UserDaoTest { 
	
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;	
	
	public UserDaoTest() {
		
	}
		
	@BeforeEach
	public void setUp() {	
		ApplicationContext context = new AnnotationConfigApplicationContext(TestDaoFactory.class);
		
		this.dao = context.getBean("userDao", UserDao.class);
		user1 = new User("user1", "sungkim", "5678");
		user2 = new User("user2", "brucelee", "9012");
		user3 = new User("user3", "haechoi", "1234");
	}
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {				
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		User userget1 = dao.get(user1.getId());
		assertEquals(user1.getName(), userget1.getName());
		assertEquals(user1.getPassword(), userget1.getPassword());
		
		User userget2 = dao.get(user2.getId());		
		assertEquals(user2.getName(), userget2.getName());
		assertEquals(user2.getPassword(), userget2.getPassword());		
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);

		dao.add(user1);
		assertEquals(dao.getCount(), 1);
		
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		dao.add(user3);
		assertEquals(dao.getCount(), 3);		
	}
	
	@Test
	public void getUserFailure() throws SQLException, ClassNotFoundException {		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);	
		
//		public interface Executable {
//
//			void execute() throws Throwable;
//
//		}
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, 
				() -> {dao.get("unknown_id");});	
	}	

}
