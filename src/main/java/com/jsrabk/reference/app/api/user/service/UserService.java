package com.jsrabk.reference.app.api.user.service;

import java.util.List;

import com.jsrabk.reference.app.api.model.User;

public interface UserService {

   User save(User user);
   User getById(long id);
   List<User> list();
   void deleteById(long id);

   User findByEmail(String email);
}
