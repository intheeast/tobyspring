package com.intheeast.springframe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 전략 패턴의 strategy 
public interface StatementStrategy {
	
	// execute...
	PreparedStatement makePreparedStatement(Connection c) throws SQLException; 
}
