package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.impl.UserDetailServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.impl.UserServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto user, BindingResult result) {
//        System.out.println(user.toString());
        if (result.hasErrors()) {
            return "register";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Password and confirmation password do not match");
        }
        Optional<User> userDetails=null;
        if (userDetailsService!=null){
            System.out.println("is not null");
        }
        try {
            userDetails = userService.getUserByUsername(user.getUsername());
            if(userDetails.isPresent()) {
                result.rejectValue("username", "error.user", "Username is already in use");
                return "register";
            }
        } catch (UsernameNotFoundException ex) {}
        userDetails=userDetailsService.loadByEmail(user.getEmail());
        if( userDetails.isPresent() ){
            result.rejectValue("email", "error.user", "Email is already in use");
            return "register";
        }
        Optional<User> userPresent=userService.getUserByUsername(user.getUsername());

        if (userPresent.isPresent()){
            result.rejectValue("username", "error.user", "This username is taken");
            return "register";
        }

        
        userService.saveUser(user);
        return "/login";
    }
}
