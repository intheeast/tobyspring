package com.intheeast.springframe.service;

import java.util.List;
import java.util.Optional;

import com.intheeast.springframe.domain.User;

public interface UserService {
	void add(User user);
	void deleteAll();
	void update(User user);	
	Optional<User> get(String id);
	List<User> getAll();
	
	void upgradeLevels();
}

