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
	@Autowired UserDao userDao; 
	@Autowired DataSource dataSource;
	
	private User user1;
	private User user2;
	private User user3;	
		
	@BeforeEach
	public void setUp() {		
		this.user1 = new User("gyumee", "박성철", "springno1", "user1@ksug.org", Level.BASIC, 1, 0);
		this.user2 = new User("leegw700", "이길원", "springno2", "user2@ksug.org", Level.SILVER, 55, 10);
		this.user3 = new User("bumjin", "박범진", "springno3", "user3@ksug.org", Level.GOLD, 100, 40);
	}
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {				
		userDao.deleteAll();
		assertEquals(userDao.getCount(), 0);
		
		userDao.add(user1);
		userDao.add(user2);
		assertEquals(userDao.getCount(), 2);
		
		Optional<User> Optuserget1 = userDao.get(user1.getId());
		if(!Optuserget1.isEmpty()) {
			User userget = Optuserget1.get();
			assertEquals(user1.getName(), userget.getName());
			assertEquals(user1.getPassword(), userget.getPassword());
		}
		
		Optional<User> Optuserget2 = userDao.get(user2.getId());
		if(!Optuserget2.isEmpty()) {
			User userget = Optuserget2.get();
			assertEquals(user2.getName(), userget.getName());
			assertEquals(user2.getPassword(), userget.getPassword());
		}
				
	}
	
	@Test
	public void getUserFailure() throws SQLException, ClassNotFoundException {		
		userDao.deleteAll();
		assertEquals(userDao.getCount(), 0);		
		
		Optional<User> Optuserget = userDao.get("unknown_id");
		assertTrue(Optuserget.isEmpty());	
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {		
		userDao.deleteAll();
		assertEquals(userDao.getCount(), 0);

		userDao.add(user1);
		assertEquals(userDao.getCount(), 1);
		
		userDao.add(user2);
		assertEquals(userDao.getCount(), 2);
		
		userDao.add(user3);
		assertEquals(userDao.getCount(), 3);		
	}	
	
	@Test
	public void getAll() throws SQLException  {
		userDao.deleteAll();
		List<User> users0 = userDao.getAll();
		assertEquals(users0.size(), 0);
		
		userDao.add(user1); 
		List<User> users1 = userDao.getAll();
		assertEquals(users1.size(), 1);
		checkSameUser(user1, users1.get(0));
		
		userDao.add(user2); 
		List<User> users2 = userDao.getAll();
		assertEquals(users2.size(), 2);
		checkSameUser(user1, users2.get(0));  
		checkSameUser(user2, users2.get(1));
		
		userDao.add(user3); 
		List<User> users3 = userDao.getAll();
		assertEquals(users3.size(), 3);	
		checkSameUser(user3, users3.get(0));  
		checkSameUser(user1, users3.get(1));  
		checkSameUser(user2, users3.get(2));
	}
	
	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getPassword(), user2.getPassword());
		assertEquals(user1.getEmail(), user2.getEmail());
		assertEquals(user1.getLevel(), user2.getLevel());
		assertEquals(user1.getLogin(), user2.getLogin());
		assertEquals(user1.getRecommend(), user2.getRecommend());
	}
	
	@Test
	public void duplciateKey() throws SQLException {
		userDao.deleteAll();
		
		userDao.add(user1);
		assertThrows(DuplicateKeyException.class, () -> userDao.add(user1));
	}
	
	@Test
	public void sqlExceptionTranslate() {
		userDao.deleteAll();
		
		try {
			userDao.add(user1);
			userDao.add(user1);
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
		userDao.deleteAll();
		
		userDao.add(user1);
		userDao.add(user2);
		
		user1.setName("���α�");
		user1.setPassword("springo6");
		user1.setEmail("user6@ksug.org");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		userDao.update(user1);
		
		Optional<User> Optuser1update = userDao.get(user1.getId());
		
		if(!Optuser1update.isEmpty()) {
			User user1update = Optuser1update.get();
			checkSameUser(user1, user1update);
		}	
		
		Optional<User> Optuser2update = userDao.get(user2.getId());
		
		if(!Optuser2update.isEmpty()) {
			User user2update = Optuser2update.get();
			checkSameUser(user2, user2update);
		}
	}

		
}
