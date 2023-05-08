package com.siw.uniroma3.it.siw_lavendetta.config;

import com.siw.uniroma3.it.siw_lavendetta.impl.EmailServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.impl.ReviewServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.impl.UserServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ReviewRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.tokens.VerificationTokenRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import com.siw.uniroma3.it.siw_lavendetta.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public UserService userService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, EmailServiceImpl emailService) {
        return new UserServiceImpl(userRepository, new BCryptPasswordEncoder(), verificationTokenRepository, emailService);
    }

    @Bean
    public ReviewService reviewService(ReviewRepository reviewRepository, FilmRepository filmRepository, UserRepository userRepository){
        return new ReviewServiceImpl(reviewRepository,filmRepository,userRepository);
    }

}
