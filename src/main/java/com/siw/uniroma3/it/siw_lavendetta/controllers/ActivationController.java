package com.siw.uniroma3.it.siw_lavendetta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActivationController {
    @GetMapping("/activation/success")
    public String showSuccessView(@RequestParam String token) {
        return "activation_success";
    }

}
