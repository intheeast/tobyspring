package com.intheeast.springframe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
// 어떻게 JdbcTemplate이 템플릿 메서드 패턴을 사용하는지:
// JdbcTemplate은 데이터베이스에 접근하는 반복적인 단계들(예: SQL 실행, 예외 처리, 리소스 해제 등)을 캡슐화하여, 
// (이러한 단계들은 어떤 데이터베이스와의 트랜잭션 CRUD 작업을 수행하더라도 순차적으로 수행합니다)
// 사용자가 이러한 작업을 직접 작성할 필요 없이 편리하게 데이터베이스 작업을 수행할 수 있도록 합니다. 
// 템플릿 메서드 패턴의 원리에 따라, 
// JdbcTemplate은 기본적인 데이터베이스 접근 로직을 정의하고, 
// 특정 작업(예: 각 row을 객체로 매핑하는 작업)은 클라이언트 코드에서 구현하도록 합니다.
import org.springframework.jdbc.core.JdbcTemplate;

import com.intheeast.springframe.domain.User;

public class UserDao {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
			
		this.dataSource = dataSource;
	}	
	
	public void add(final User user) throws SQLException {
		this.jdbcTemplate.
		update("insert into users(id, name, password) values(?,?,?)",
				user.getId(), 
				user.getName(), 
				user.getPassword());
		
		//this.jdbcTemplate.update
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		
		Connection c = this.dataSource.getConnection();
		
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
		this.jdbcTemplate.update("delete from users");
	}
	
	public int getCount() throws SQLException, ClassNotFoundException  {
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = this.dataSource.getConnection();
			
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
