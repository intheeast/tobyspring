package com.intheeast.springframe.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.intheeast.springframe.dao.DaoFactory;
import com.intheeast.springframe.dao.UserDao;

public class UserServiceRun {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(TestServiceFactory.class);
		DataSourceTransactionManager tm = context.getBean("transactionManager", DataSourceTransactionManager.class);
		
		System.out.println("UserServiceRun.main:Hello World");

	}

}
