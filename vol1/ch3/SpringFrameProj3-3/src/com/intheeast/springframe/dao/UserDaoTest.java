package com.intheeast.springframe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

//import org.junit.jupiter.api.AssertThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.intheeast.springframe.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoFactory.class})
public class UserDaoTest {
	
	//@Autowired
	//private ApplicationContext context;	
	
	// JUnit 단위 테스트...!!!
	 
	@Autowired  // Annotation based Configuration Meta data.
	private UserDao dao; 
	
	// Test Fixture!!!
	private User user1;
	private User user2;
	private User user3;
	
		
	@BeforeEach
	public void setUp() {	
		
		user1 = new User("user1", "sungkim", "0000");
		user2 = new User("user2", "brucelee", "1111");
		user3 = new User("user3", "haechoi", "2222");
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
		
		/*
		 public static <T extends Throwable> T assertThrows(
		 	Class<T> expectedType, 
		 	Executable executable) {
				return AssertThrows.assertThrows(expectedType, executable);
		 }
		 
		 @FunctionalInterface
		 public interface Executable {
			// abstract method only one을 정의
			void execute() throws Throwable {
				dao.get("unknown_id");			
			}

		 }
		 
		 () -> dao.get("unknown_id") 가 메소드의 아규먼트로 사용됨 : 람다가 First Class Citizen.
		  또한 이런 람다식은 리턴값도 될 수 있다. 왜 람다가 First Class Citizen.
		  expression : 값으로 평가된다. 값을 리턴(반환)한다!!!
		 */
		Assertions.assertThrows(EmptyResultDataAccessException.class, 
				() -> dao.get("unknown_id"));
	}	
	
//	@Test
//	public void exhaust() throws SQLException {
//		
//	}

}
