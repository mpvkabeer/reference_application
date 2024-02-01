package com.jsrabk.reference.app.api.common.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsrabk.reference.app.common.security.CustomUserDetailsService;
import com.jsrabk.reference.app.common.security.jwt.JwtHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/open-api")
public class OpenAPIController {

   @Autowired
   private JwtHelper jwtHelper;
   
   @Autowired
   CustomUserDetailsService customUserDetailsService;

   /*---Add new user---*/
   @PostMapping("/login")
   public ResponseEntity<?> authLogin(@RequestBody String inputData, HttpServletRequest request, HttpServletResponse response) {

	   try {
		  JSONObject userDTO = new JSONObject(inputData);
		  String email = userDTO.getString("email");
		  String password = userDTO.getString("password");
	      UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

	      if(new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
	    	  String token = jwtHelper.generateToken(userDetails);
	    	  return ResponseEntity.ok().body("Authenticated. Token:" + token);
	      } else {
	    	  return ResponseEntity.ok().body("Invalid Username or Password");
	      }
	   }catch (Exception e) {
		   e.printStackTrace();
		   return ResponseEntity.ok().body("Invalid Input");
	   }
	   
   }

}