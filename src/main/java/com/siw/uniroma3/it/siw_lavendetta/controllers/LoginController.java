package com.siw.uniroma3.it.siw_lavendetta.controllers;


import com.siw.uniroma3.it.siw_lavendetta.impl.UserServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession
            session, Model model) {
        User user = userService.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            if (user.isEnabled()) {
                session.setAttribute("user", user);
                return "redirect:/home";
            } else {
                model.addAttribute("emailNotVerified", true);
                return "login";
            }
        } else {
            model.addAttribute("loginError", true);
            return "login";
        }
    }
}