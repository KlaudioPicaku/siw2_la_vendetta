package com.siw.uniroma3.it.siw_lavendetta.controllers;


import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.impl.UserServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

//    @Autowired
//    OAuth2AuthorizedClientService clientService;


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        System.out.println("get eseguita");
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginMethod(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, HttpSession session, Model model) {
        System.out.println("post eseguita");
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "login";
        }

        Optional<User> user = userService.getUserByUsername(userDto.getUsername());
        System.out.println(user);


        if (!user.isPresent() || !userService.passwordMatch(user.get(), userDto.getPassword())) {
            model.addAttribute("loginError", true);
            return "login";
        }

        if (!user.get().isEnabled()) {
            model.addAttribute("emailNotVerified", true);
            return "login";
        }

        session.setAttribute("user", user);

        return "redirect:/home";
    }

    //    @GetMapping("/oauth2/callback/code/github")
    //    public String handleOAuth2Callback(HttpServletRequest request) {
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //        System.out.println(authentication.getPrincipal());
    ////        if (authentication instanceof OAuth2AuthenticationToken) {
    ////            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
    ////            // Access the OAuth 2.0 authentication token
    ////            String accessToken = oauthToken.getAccessToken().getTokenValue();
    ////            // Perform actions with the access token
    ////            userService.processOAuth2User(authenticationToken);
    ////        }
    //        return "redirect:/";
    //    }
}