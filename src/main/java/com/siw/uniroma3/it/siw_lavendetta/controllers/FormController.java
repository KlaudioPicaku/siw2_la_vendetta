package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.dto.ReviewDto;
import com.siw.uniroma3.it.siw_lavendetta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {
    UserService userService;
    @Autowired
    public FormController(UserService userService){
        this.userService=userService;

    }

    @GetMapping("/load-review-form")
    public String loadReviewForm(Model model){
        model.addAttribute("review",new ReviewDto());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "error/403";
        }
//        model.addAttribute("loggedInUserId",userService.getUserByUsername(authentication.getName()).getId());
        return "fragments/review_form_fragment";
    }
    @GetMapping("/load-reviews")
    public String loadReviewsModal(){
        return "fragments/modals/reviews_modal";
    }


}
