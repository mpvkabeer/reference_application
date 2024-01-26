package com.jsrabk.reference.app.api.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsrabk.reference.app.api.model.User;
import com.jsrabk.reference.app.api.user.service.UserService;

@RestController
public class UserController {

   @Autowired
   private UserService userService;

   /*---Add new user---*/
   @PostMapping("/user")
   public ResponseEntity<?> save(@RequestBody User user) {
	  
      User savedUser  = userService.save(user);
      long id = savedUser.getId();
      return ResponseEntity.ok().body("New User has been saved with ID:" + id);
   }
   
   /*---Get a user by id---*/
   @GetMapping("/user/{id}")
   public ResponseEntity<User> get(@PathVariable("id") long id) {
	  User user = userService.getById(id);
      return ResponseEntity.ok().body(user);
   }

//   /*---get all users---*/
//   @GetMapping("/users")
//   public ResponseEntity<List<User>> list() {
//	  System.out.println("Getting user List inside UserController"); 
//      List<User> users = userService.list();
//      System.out.println("Inside UserController"+users);
//      for(User user : users) {
//    	  System.out.println("Id:"+ user.getId());
//    	  System.out.println("Email:"+ user.getEmail());
//    	  System.out.println("Password:"+ user.getPassword());
//    	  System.out.println("IsLoggedIn:"+ user.getIsLoggedIn());
//    	  System.out.println("StatusId:"+ user.getStatus().getId());
//    	  System.out.println("Status:"+ user.getStatus().getName());
//      }

     
//      return ResponseEntity.ok().body(users);
//   }

   /*---Update a user by id---*/
   @PutMapping("/user/{id}")
   public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody User user) {
	  user.setId(id);
      userService.save(user);
      return ResponseEntity.ok().body("User has been updated successfully.");
   }

   /*---Delete a user by id---*/
   @DeleteMapping("/user/{id}")
   public ResponseEntity<?> delete(@PathVariable("id") long id) {
      userService.deleteById(id);
      return ResponseEntity.ok().body("User has been deleted successfully.");
   }
   
}