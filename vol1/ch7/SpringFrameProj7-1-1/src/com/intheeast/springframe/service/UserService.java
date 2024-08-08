package com.intheeast.springframe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intheeast.springframe.domain.User;

@Service
@Transactional // UserService의 모든 메소드들에게 적용된다. 
               // 단 @Transactional 어노테이션이 재적용된것은,
               // 이전의 @Transactional 적용을 무효화시킨다 
public interface UserService {
	void add(User user);
	void deleteAll();
	void update(User user);
	
	@Transactional(readOnly=true)
	Optional<User> get(String id);
	
	@Transactional(readOnly=true)
	List<User> getAll();
	
	//@Transactional
	void upgradeLevels();
}
