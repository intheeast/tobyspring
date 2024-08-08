package com.intheeast.springframe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
	
	/*
	    <property name="driverClass" value="com.mysql.jdbc.Driver" />
		<property name="url" value="jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8" />
		<property name="username" value="root" />
		<property name="password" value="1234" /> 
	 
	 */
	private String driverClass;
	private String url;
	private String username;
	private String password;
	
	public DConnectionMaker() {
		
	}
	
	public void setDriverClass(String driverclass) {
		this.driverClass = driverclass;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName(this.driverClass);
		Connection c = DriverManager.getConnection(this.url, 
				this.username,
				this.password);
		return c;
	}

	/*
	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8", 
				"root",
				"1234");
		return c;
	}
	*/

}
