package com.intheeast.springframe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.intheeast.springframe.domain.Level;
import com.intheeast.springframe.domain.User;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoFactory.class})
public class UserDaoTest {	
	 
	@Autowired UserDao dao; 
	@Autowired DataSource dataSource;
	
	private User user1;
	private User user2;
	private User user3;
	
		
	@BeforeEach
	public void setUp() {	
		
		this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
		this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
		this.user3 = new User("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);
	}
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {				
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		Optional<User> Optuserget1 = dao.get(user1.getId());
		if(!Optuserget1.isEmpty()) {
			User userget = Optuserget1.get();
			assertEquals(user1.getName(), userget.getName());
			assertEquals(user1.getPassword(), userget.getPassword());
		}
		
		Optional<User> Optuserget2 = dao.get(user2.getId());
		if(!Optuserget2.isEmpty()) {
			User userget = Optuserget2.get();
			assertEquals(user2.getName(), userget.getName());
			assertEquals(user2.getPassword(), userget.getPassword());
		}			
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
		
		Optional<User> Optuserget = dao.get("unknown_id");
		assertTrue(Optuserget.isEmpty());		
	}
	
	@Test
	public void getAll() throws SQLException  {
		dao.deleteAll();
		List<User> users0 = dao.getAll();
		assertEquals(users0.size(), 0);
		
		dao.add(user1); 
		List<User> users1 = dao.getAll();
		assertEquals(users1.size(), 1);
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2); 
		List<User> users2 = dao.getAll();
		assertEquals(users2.size(), 2);
		checkSameUser(user1, users2.get(0));  
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3); 
		List<User> users3 = dao.getAll();
		assertEquals(users3.size(), 3);	
		checkSameUser(user3, users3.get(0));  
		checkSameUser(user1, users3.get(1));  
		checkSameUser(user2, users3.get(2)); 
		
		
		return ;
	}
	
	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getPassword(), user2.getPassword());
		
		assertEquals(user1.getLevel(), user2.getLevel());
		assertEquals(user1.getLogin(), user2.getLogin());
		assertEquals(user1.getRecommend(), user2.getRecommend());
	}
	
	@Test
	public void duplciateKey() throws SQLException {
		dao.deleteAll();
		
		dao.add(user1);
		assertThrows(DuplicateKeyException.class, () -> dao.add(user1));
	}
	
	@Test
	public void sqlExceptionTranslate() {
		dao.deleteAll();
		
		try {
			dao.add(user1);
			dao.add(user1);
		}
		catch(DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException)ex.getCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);			
			DataAccessException transEx = set.translate(null, null, sqlEx);
			assertEquals(DuplicateKeyException.class, transEx.getClass());
		}
	}
	
	@Test
	public void update() {
		dao.deleteAll();
		
		dao.add(user1);
		dao.add(user2);
		
		user1.setName("오민규");
		user1.setPassword("springo6");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		dao.update(user1);
		
		Optional<User> Optuser1update = dao.get(user1.getId());
		
		if(!Optuser1update.isEmpty()) {
			User user1update = Optuser1update.get();
			checkSameUser(user1, user1update);
		}	
		
		Optional<User> Optuser2update = dao.get(user2.getId());
		
		if(!Optuser2update.isEmpty()) {
			User user2update = Optuser2update.get();
			checkSameUser(user2, user2update);
		}
	}
	
//	@Test
//	public void SimpleGetOne() {
//		dao.deleteAll();		
//		dao.add(user1);		
//		
//		// org.springframework.jdbc.InvalidResultSetAccessException
//		dao.get(user1.getId());			
//	}

		
}
