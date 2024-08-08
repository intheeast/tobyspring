package com.intheeast.springframe.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.intheeast.springframe.domain.User;

public class UserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);		
	}	
	
	public void add(final User user) {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
						user.getId(), 
						user.getName(), 
						user.getPassword());
	}	
	
	/*
	 private RowMapper<User> userRowMapper() {
		// (rs, rowNum) -> rs.getInt(1)
        return ((rs, rowNum) -> {
        	User user = new User();
        	user.setId(rs.getString("id"));
        	user.setName(rs.getString("name"));
        	user.setPassword(rs.getString("password"));            
            return user;
        });
    }	
	 */
	
	private RowMapper<User> userRowMapper = (rs, rowNum) -> {
	    User user = new User();
	    user.setId(rs.getString("id"));
	    user.setName(rs.getString("name"));
	    user.setPassword(rs.getString("password"));
	    return user;
	};
	
	public Optional<User> get(String id) {
	    String sql = "select * from users where id = ?";
	    
//	    String hello = null; // 참조하는 스트링 클래스 객체가 없다
//	    hello.toLowerCase(); // object method...
	    
	    //jdbcTemplate.queryForObject(sql, userRowMapper);	    
	    
	    try (Stream<User> stream = 
	    		jdbcTemplate.queryForStream(
	    				sql, 
	    				userRowMapper, 
	    				id)) {
	        return stream.findFirst();
	    } catch (DataAccessException e) {
	        return Optional.empty();
	    }
	}	
	
	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}	
	
	public int getCount() {
	    List<Integer> result = jdbcTemplate.query("select count(*) from users", 
	    		(rs, rowNum) -> rs.getInt(1));
	    return (int) DataAccessUtils.singleResult(result);
	}	
	
	public List<User> getAll() {
		return this.jdbcTemplate.query(
				"select * from users order by id",
				userRowMapper
				);
	}	
}
