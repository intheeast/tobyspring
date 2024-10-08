package com.intheeast.springframe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import com.intheeast.springframe.domain.User;

// 전략 패턴의 Client
public class UserDao {
	//private DataSource dataSource;
	
	private JdbcContext jdbcContext;
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;		
	}
	
	public void add(final User user) throws SQLException {
		this.jdbcContext.workWithStatementStrategy(
				// strategy Concrete Class
				new StatementStrategy() {			
					public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
						PreparedStatement ps = 
							c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
						ps.setString(1, user.getId());
						ps.setString(2, user.getName());
						ps.setString(3, user.getPassword());
						
						return ps;
					}
				}
		);
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		
		Connection c = this.jdbcContext.getDataSourceConnection();
		
		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		ps.close();
		c.close();
		
		if (user == null) throw new EmptyResultDataAccessException(1);

		return user;
	}
	
	public void deleteAll() throws SQLException {
		this.jdbcContext.workWithStatementStrategy(
				// strategy Concrete Class
			new StatementStrategy() {
				public PreparedStatement makePreparedStatement(Connection c)
						throws SQLException {
					return c.prepareStatement("delete from users");
				}
			}
		);
	}
	
	public int getCount() throws SQLException, ClassNotFoundException  {
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = this.jdbcContext.getDataSourceConnection();
			
			ps = c.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if ( rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {					
				}
			}			
			if ( ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {					
				}
			}
			if ( c!= null) {
				try {
					c.close();
				} catch (SQLException e) {					
				}
			}			
		}		
	}

}
