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
	
	public void add(final User user) /*throws DataAccessException*/ {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
						user.getId(), 
						user.getName(), 
						user.getPassword());
	}	
	
	private RowMapper<User> userRowMapper = (rs, rowNum) -> {
	    User user = new User();
	    user.setId(rs.getString("id"));
	    user.setName(rs.getString("name"));
	    user.setPassword(rs.getString("password"));
	    return user;
	};
	
	public Optional<User> get(String id) throws DataAccessException {
	    String sql = "select * from users where id = ?";	
	    
	    //jdbcTemplate.queryForObject(sql, userRowMapper);
	    
	    try (Stream<User> stream = jdbcTemplate.queryForStream(sql, userRowMapper, id)) {
	        return stream.findFirst();
	    } catch (DataAccessException e) {
	        return Optional.empty();
	    }
	}	
	
	public void deleteAll() throws DataAccessException {
		this.jdbcTemplate.update("delete from users");
//		try {
//			
//		}
//		catch(Da)
	}	
	
	public int getCount() throws DataAccessException {
	    List<Integer> result = jdbcTemplate.query("select count(*) from users", 
	    		(rs, rowNum) -> rs.getInt(1));
	    return (int) DataAccessUtils.singleResult(result);
	}	
	
	public List<User> getAll() throws DataAccessException {
		return this.jdbcTemplate.query("select * from users order by id",
				userRowMapper
		);
	}	
}
