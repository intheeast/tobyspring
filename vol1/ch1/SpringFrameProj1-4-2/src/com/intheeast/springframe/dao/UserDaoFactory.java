package com.intheeast.springframe.dao;


// 제3자 또는 Framework으로 간주!!!
public class UserDaoFactory {
	// Spring Bean(X)
	public UserDao userDao() {
		// Dependency Injection?
		UserDao dao = new UserDao(connectionMaker());
		return dao;
	}

	// Spring Bean(X)
	public ConnectionMaker connectionMaker() {
		ConnectionMaker connectionMaker = new DConnectionMaker();
		return connectionMaker;
	}
}
