package com.jsrabk.reference.app.api.user.service;

import java.util.List;
import java.util.Optional;

import com.jsrabk.reference.app.api.model.User;

public interface UserService {

   User save(User user);
   User getById(long id);
   List<User> list();
   void deleteById(long id);
   User findByUsername(String username);
   User findByEmail(String email);
}
