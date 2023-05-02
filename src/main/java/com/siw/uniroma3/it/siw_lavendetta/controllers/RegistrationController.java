package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.impl.UserDetailServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.impl.UserServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

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
    public String registerUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (userDetails != null) {
            result.rejectValue("username", "error.user", "Username is already in use");
            return "register";
        }
        userDetails=userDetailsService.loadByEmail(user.getEmail());
        if (userDetails != null) {
            result.rejectValue("email", "error.user", "Email is already in use");
            return "register";
        }

        userService.saveUser(user);
        return "/login";
    }
}
