package com.jsrabk.reference.app.validator;


//import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jsrabk.reference.app.api.model.User;
//import com.jsrabk.reference.app.api.model.User;
//import com.jsrabk.reference.app.api.user.service.UserService;
 
@Component
public class UserValidator implements Validator {
 
    // common-validator library.
//    private EmailValidator emailValidator = EmailValidator.getInstance();
 
//    private UserService userService;
 
    // The classes are supported by this validator.
    public boolean supports(Class<?> clazz) {
        return clazz == User.class;
    }
 
    public void validate(Object target, Errors errors) {
        //User appUserForm = (User) target;
 
        // Check the fields of AppUserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.appUserForm.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appUserForm.confirmPassword");
 
//TODO:
//        if (!this.emailValidator.isValid(appUserForm.getEmail())) {
//            // Invalid email.
//            errors.rejectValue("email", "Pattern.appUserForm.email");
//        } else if (appUserForm.getUserId() == null) {
//            User dbUser = userService.findByEmail(appUserForm.getEmail());
//            if (dbUser != null) {
//                // Email has been used by another account.
//                errors.rejectValue("email", "Duplicate.appUserForm.email");
//            }
//        }
// 
//        if (!errors.hasFieldErrors("username")) {
//            User dbUser = userService.findByUsername(appUserForm.getUsername());
//            if (dbUser != null) {
//                // Username is not available.
//                errors.rejectValue("username", "Duplicate.appUserForm.username");
//            }
//        }
 
//        if (!errors.hasErrors()) {
//            if (!appUserForm.getConfirmPassword().equals(appUserForm.getPassword())) {
//                errors.rejectValue("confirmPassword", "Match.appUserForm.confirmPassword");
//            }
//        }
    }
 
}