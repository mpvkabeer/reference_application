package com.jsrabk.reference.app.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name="user")
//@Document(indexName = "java_test_app", type = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    private String username;
    private String password;
    private boolean isLoggedIn;
   
	@ManyToOne
	@JoinColumn(name = "StatusId")
    private Status status;
 
//    public User() {
// 
//    }
 
//    public User(Long id, String username, String first_name, String last_name, //
//            boolean enabled, String gender, //
//            String email, String password) {
//        super();
//        this.id = id;
//        this.username = username;
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.enabled = enabled;
//        this.gender = gender;
//        this.email = email;
//        this.password = password;
//    }
// 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    } 
 
    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }
 
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

     public Status getStatus() {
        return status;
    }
 
    public void setStatus(Status status) {
        this.status = status;
    }
 
}