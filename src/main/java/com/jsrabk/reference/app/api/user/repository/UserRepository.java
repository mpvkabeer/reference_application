package com.jsrabk.reference.app.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsrabk.reference.app.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
//TODO:	
//   User findByEmail(String email);
//
//   User findByUsername(String username);

}
