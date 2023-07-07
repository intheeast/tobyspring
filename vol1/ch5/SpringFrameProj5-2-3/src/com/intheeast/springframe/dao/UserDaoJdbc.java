package com.intheeast.springframe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.intheeast.springframe.domain.Level;
import com.intheeast.springframe.domain.User;



public class UserDaoJdbc implements UserDao {
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<User> userMapper = (rs, rowNum) -> {
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		user.setLevel(Level.valueOf(rs.getInt("level")));
		user.setLogin(rs.getInt("login"));
		user.setRecommend(rs.getInt("recommend"));
		return user;
		
	};	

	@Override
	public void add(User user) {
		this.jdbcTemplate.update(
				"insert into users(id, name, password, level, login, recommend) " +
						"values(?,?,?,?,?,?)", 
						user.getId(), 
						user.getName(), 
						user.getPassword(), 
						user.getLevel().intValue(), 
						user.getLogin(), 
						user.getRecommend());

	}

	@Override
	public Optional<User> get(String id) {
		String sql = "select * from users where id = ?";	    		
	    
	    try (Stream<User> stream = jdbcTemplate.queryForStream(sql, this.userMapper, id)) {
	        return stream.findFirst();
	    } catch (DataAccessException e) {
	        return Optional.empty();
	    }
	}

	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id",
				this.userMapper
		);
	}

	@Override
	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");

	}

	@Override
	public int getCount() {
		List<Integer> result = jdbcTemplate.query("select count(*) from users", 
	    		(rs, rowNum) -> rs.getInt(1));
	    return (int) DataAccessUtils.singleResult(result);
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(
				"update users set "
				+ "name = ?, "
				+ "password = ?, "
				+ "level = ?, "
				+ "login = ?, " 
				+ "recommend = ? "
				+ "where id = ? ", 
				user.getName(), 
				user.getPassword(), 
				user.getLevel().intValue(), 
				user.getLogin(), 
				user.getRecommend(),
				user.getId());		
	}
}