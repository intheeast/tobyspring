package com.intheeast.springframe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.intheeast.springframe.domain.User;

//@Repository("userDao")
public interface UserDao {
	
	void add(User user);

	Optional<User> get(String id);

	List<User> getAll();

	void deleteAll();

	int getCount();
	
	public void update(User user);
}
