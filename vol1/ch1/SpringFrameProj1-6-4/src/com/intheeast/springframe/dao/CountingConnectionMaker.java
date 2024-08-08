package com.intheeast.springframe.dao;

import java.sql.Connection;
import java.sql.SQLException;


public class CountingConnectionMaker implements ConnectionMaker {
	
	int counter = 0;
	private ConnectionMaker realConnectionMaker;
	
	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		// realConnectionMaker는 IoC로부터 주입받음(Dependency Injection)
		this.counter++; // <- this.counter = this.counter + 1;
		return realConnectionMaker.makeConnection();
	}
	
	public int getCounter() {
		return this.counter;
	}

}
