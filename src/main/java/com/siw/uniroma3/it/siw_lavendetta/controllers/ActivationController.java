package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import com.siw.uniroma3.it.siw_lavendetta.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActivationController {
    @Autowired
    VerificationTokenService verificationTokenService;
    @GetMapping("/activation/success")
    public String showSuccessView(@RequestParam("token") String token) {
            return "activation_success";
    }

    @GetMapping("/activation/failed")
    public String showActivationFailed(@RequestParam("token") String token){ return "activation_failed";}

}
