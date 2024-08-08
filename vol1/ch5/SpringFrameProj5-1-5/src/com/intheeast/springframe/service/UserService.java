package com.intheeast.springframe.service;

import java.util.List;

import javax.sql.DataSource;

import com.intheeast.springframe.dao.UserDao;
import com.intheeast.springframe.domain.Level;
import com.intheeast.springframe.domain.User;


public class UserService {
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	private DataSource dataSource;  			

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//@Transactional --> Spring AOP..Java Dynaminc Proxy..[CGLib..AspectJ]..Reflection...
	public void upgradeLevels() {
		// 하나의 트랜잭션으로 보자!!!
		List<User> users = userDao.getAll();  
		for(User user : users) {  
			if (canUpgradeLevel(user)) {  
				upgradeLevel(user);  // 
			}
		}
		// commit을 함
	}
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel(); 
		switch(currentLevel) {                                   
		case BASIC: 
			return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER); 
		case SILVER: 
			return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD: 
			return false;
		default: throw new IllegalArgumentException("Unknown Level: " + currentLevel); 
		}
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}
	
	public void add(User user) {
		if (user.getLevel() == null) 
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
