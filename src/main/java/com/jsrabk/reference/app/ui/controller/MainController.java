package com.jsrabk.reference.app.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsrabk.reference.app.api.model.User;
import com.jsrabk.reference.app.api.user.service.UserService;
import com.jsrabk.reference.app.formbean.UserForm;
import com.jsrabk.reference.app.validator.UserValidator;
 
@Controller
public class MainController {
 
   @Autowired
   private UserService userService;
  
   @Autowired
   private UserValidator appUserValidator;
 
   // Set a form validator
   @InitBinder
   protected void initBinder(WebDataBinder dataBinder) {
      // Form target
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      if (target.getClass() == UserForm.class) {
         dataBinder.setValidator(appUserValidator);
      }
      // ...
   }
 
   @RequestMapping("/welcome")
   public String viewHome(Model model) {
 
      return "welcomePage";
   }
   
   @RequestMapping("/members")
   public String viewMembers(Model model) {
 
      List<User> list = userService.list();
      
      model.addAttribute("title", "Members Page");
      model.addAttribute("members", list);
 
      return "membersPage";
   }
 
   @RequestMapping("/registerSuccessful")
   public String viewRegisterSuccessful(Model model) {
 
      return "registerSuccessfulPage";
   }
 
   // Show Register page.
   @RequestMapping(value = "/register", method = RequestMethod.GET)
   public String viewRegister(Model model) {
 
      UserForm form = new UserForm();
      //List<Country> countries = countryDAO.getCountries();
 
      model.addAttribute("appUserForm", form);
//      model.addAttribute("countries", countries);
 
      return "registerPage";
   }
 
   // This method is called to save the registration information.
   // @Validated: To ensure that this Form
   // has been Validated before this method is invoked.
   @RequestMapping(value = "/register", method = RequestMethod.POST)
   public String saveRegister(Model model, //
         @ModelAttribute("userForm") @Validated UserForm userForm, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {
 
      // Validate result
      if (result.hasErrors()) {
         return "registerPage";
      }
      User newUser= new User();
      newUser.setUsername(userForm.getUsername());
      
      try {
         //newUser = userDao.createUser(userForm);
    	  userService.save(newUser);
      }
      // Other error!!
      catch (Exception e) {
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "registerPage";
      }
 
      redirectAttributes.addFlashAttribute("flashUser", newUser);
       
      return "redirect:/registerSuccessful";
   }
 
   // Show Register page.
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String viewLOgin(Model model) {
 
      return "login";
   }
   
}