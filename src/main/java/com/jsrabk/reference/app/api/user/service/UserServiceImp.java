package com.jsrabk.reference.app.api.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsrabk.reference.app.api.model.User;
import com.jsrabk.reference.app.api.user.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

// Reference: https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html 
	
	
   @Autowired
   private UserRepository userDao;

   	
	public User save(User user) {
		return userDao.save(user);
	}
	
	public User getById(long id) {
		return userDao.findById(id).get();
	}
	
	public List<User> list() {
		return userDao.findAll();
	}

	public void deleteById(long id) {
		userDao.deleteById(id);
	}

	public User findByEmail(String email) {
		return findByEmail(email);
	}

}
