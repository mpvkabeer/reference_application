package com.jsrabk.reference.app.formbean;

public class UserForm {
 
    private Long userId;
    private String username;
    private String first_name;
    private String last_name;
    private boolean enabled;
    private String gender;
    private String email;
    private String password;
    private String confirmPassword;
 
    public UserForm() {
 
    }
 
    public UserForm(Long userId, String username, //
            String first_name, String last_name, boolean enabled, //
            String gender, String email, //
            String password, String confirmPassword) {
        this.userId = userId;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.enabled = enabled;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getFirstName() {
        return first_name;
    }
 
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
 
    public String getLastName() {
        return last_name;
    }
 
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
 
    public boolean isEnabled() {
        return enabled;
    }
 
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
 
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getConfirmPassword() {
        return confirmPassword;
    }
 
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}