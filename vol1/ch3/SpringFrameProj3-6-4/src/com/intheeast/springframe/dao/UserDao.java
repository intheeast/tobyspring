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
import org.springframework.lang.Nullable;

import com.intheeast.springframe.domain.User;

public class UserDao {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
			
		this.dataSource = dataSource;
	}	
	
	public void add(final User user) throws DataAccessException {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
						user.getId(), 
						user.getName(), 
						user.getPassword());
	}	
	
	// java.lang.NoSuchMethodError:
	// org.springframework.dao.support.DataAccessUtils.nullableSingleResult()
	// : DataAccessUtils.nullableSingleResult() �޼���� 
	//   Spring Framework 5.x ���� �������� �������� �ʾ�����, 
	//   ��� DataAccessUtils.singleResult() �޼��尡 �����Ǿ����ϴ�. 
	//   �ݸ鿡 JdbcTemplate.queryForObject() �޼��忡���� Spring Framework 5.x ���� ���Ŀ� �߰��� 
	//   DataAccessUtils.nullableSingleResult() �޼��带 ����ϰ� �ֽ��ϴ�.
	//   ����, JdbcTemplate.queryForObject() �޼��带 ȣ���� �� ���Ǵ� 
	//   Spring Framework�� ������ DataAccessUtils Ŭ������ �޼��带 ȣ���ϴ� �κ��� 
	//   Spring Framework ������ ���� ��ġ���� ���� ��쿡�� �̿� ���� NoSuchMethodError ���ܰ� �߻��� �� �ֽ��ϴ�.
	//   �� ������ �ذ��ϱ� ���ؼ���, Spring Framework�� ������ ��ġ��Ű�ų�, 
	//   �Ǵ� JdbcTemplate.queryForObject() �޼��带 ������� �ʰ� ��ſ� 
	//   jdbcTemplate.queryForStream() �޼��带 ����ϴ� ��� ������ �ڵ带 �����ؾ� �մϴ�.
	// java.lang.NoSuchMethodError:
	// org.springframework.dao.support.DataAccessUtils.nullableSingleResult()
	// : DataAccessUtils.nullableSingleResult() 메서드는 
	//   Spring Framework 5.x에서는 지원되지 않으며, 
	//   대신에 DataAccessUtils.singleResult() 메서드를 사용해야 합니다. 
	//   JdbcTemplate.queryForObject() 메서드를 사용하는 경우 Spring Framework 5.x에서 추가된 
	//   DataAccessUtils.nullableSingleResult() 메서드를 함께 사용해야 합니다.
	//   따라서, JdbcTemplate.queryForObject() 메서드를 호출하는 코드에서 
	//   Spring Framework의 DataAccessUtils 클래스의 메서드를 호출하면서 
	//   Spring Framework 버전 충돌로 인해 NoSuchMethodError 예외가 발생할 수 있습니다.
	//   이 문제를 해결하려면, Spring Framework의 버전을 확인하고, 
	//   해당 버전에 맞게 JdbcTemplate.queryForObject() 메서드를 호출하거나 
	//   jdbcTemplate.queryForStream() 메서드를 사용하는 등 적절한 대응을 해주셔야 합니다.
	/*
	 
	/*public Optional<User> get(String id) {
		String sql = "select * from users where id = ?";
		User user = jdbcTemplate.queryForObject(sql, userRowMapper(), id);
        try {
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }		
	}*/
	
	/*
	 public interface RowMapper<T> {	
		@Nullable
		T mapRow(ResultSet rs, int rowNum) throws SQLException;
	 }
	 */
	
	/*
	 User mapRow(ResultSet rs, int rowNum) throws SQLException {
	 		User user = new User();
        	user.setId(rs.getString("id"));
        	user.setName(rs.getString("name"));
        	user.setPassword(rs.getString("password"));            
            return user;
	 }
	 */
	/*
	 interface RowMapper<T> {
		 User mapRow(ResultSet rs, int rowNum) throws SQLException {
		 	User user = new User();
        	user.setId(rs.getString("id"));
        	user.setName(rs.getString("name"));
        	user.setPassword(rs.getString("password"));            
            return user;
		 }
	 }
	 */
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
	
	/*private RowMapper<User> userRowMapper = (rs, rowNum) -> {
	    User user = new User();
	    user.setId(rs.getString("id"));
	    user.setName(rs.getString("name"));
	    user.setPassword(rs.getString("password"));
	    return user;
	};*/
	
	public Optional<User> get(String id) throws DataAccessException {
		Number n;
	    String sql = "select * from users where id = ?";
	    // public <User> Stream<User> queryForStream(String sql, RowMapper<User> rowMapper, @Nullable Object... args)
	    try (Stream<User> stream = 
	    		jdbcTemplate.queryForStream(
	    				sql, 
	    				userRowMapper(),  //RowMapper<User> 
	    				id)) {
	        return stream.findFirst();
	    } catch (DataAccessException e) {
	        return Optional.empty();
	    }
	}

	
	/*
	 jdbcTemplate.query ����ص� �����ϳ� �Ʒ� query �޼ҵ� ȣ������ deprecated �Ǿ���.
	public Optional<User> get(String id) {
	    String sql = "select * from users where id = ?";
	    List<User> users = jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper<>(User.class));
	    if (users.isEmpty()) {
	        return Optional.empty();
	    } else {
	        return Optional.of(users.get(0));
	    }
	}
	*/
	
	public void deleteAll() throws DataAccessException {
		this.jdbcTemplate.update("delete from users");
	}	
	
	
	/*
	 java.lang.NoSuchMethodError:
	   org.springframework.dao.support.DataAccessUtils.nullableSingleResult()
	public int getCount()throws SQLException {
		String sql = "select count(*) from users";
		Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class);
		
		return (count != null ? count.intValue() : 0);
		//return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
		
	}*/
	/*
	 public interface RowMapper<T> {	
		@Nullable
		T mapRow(ResultSet rs, int rowNum) throws SQLException;
	 }
	 
	 // Type Inference!!!
	 int mapRow(ResultSet rs, int rowNum) throws SQLException {
	 	return (rs, rowNum) -> rs.getInt(1);	 
	 }
	 */
	// public <T> List<T> query(
	// 	String sql, 
	//  RowMapper<T> rowMapper) throws DataAccessException
	// 
	
	// 
	public int getCount() throws DataAccessException {
		
		/*
		 interface RowMapper<T> {
			 Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			 	return rs.getInt(1);
			 }
		 }
		 */
		// 타입 파라미터로 프리미티브가 올 수 없다.
		//public <T> List<Integer> query(String sql, RowMapper<Integer> rowMapper)
	    List<Integer> result = jdbcTemplate.query(
	    		"select count(*) from users", 
	    		(rs, rowNum) -> rs.getInt(1)// 아규먼트를 보고 판단, 리턴값
	    		);
	    
	    return (int) DataAccessUtils.singleResult(result);
	}
	
	
	public List<User> getAll() throws DataAccessException {
		return this.jdbcTemplate.query(
				"select * from users order by id",
				userRowMapper()
		);
	}

	
	/*public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id",
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
	}*/
}
