package com.jsrabk.reference.app.api.user.service;

import java.util.List;

import com.jsrabk.reference.app.api.model.User;
import com.jsrabk.reference.app.api.user.dto.UserDTO;

public interface UserService {

   void saveUser(UserDTO userDto);
   User save(User user);
   User getById(long id);
   List<User> list();
   List<UserDTO> findAllUsers();
   void deleteById(long id);

   User findByEmail(String email);
}
