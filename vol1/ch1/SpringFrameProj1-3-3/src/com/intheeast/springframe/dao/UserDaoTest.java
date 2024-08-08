package com.intheeast.springframe.dao;

import java.sql.SQLException;

import com.intheeast.springframe.domain.User;



public class UserDaoTest {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//ConnectionMaker connectionMaker = new DConnectionMaker();
		ConnectionMaker connectionMaker = new NConnectionMaker();
		UserDao dao = new UserDao(connectionMaker); // IoC == dependency injection?
												    

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);
			
		System.out.println(user.getId() + "\n 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
			
		System.out.println(user2.getId() + "\n 조회 성공");
	}

}
