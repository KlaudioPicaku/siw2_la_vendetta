package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.constants.GUIconstants;
import com.siw.uniroma3.it.siw_lavendetta.constants.MailSubjects;
import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.tokens.VerificationTokenRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private VerificationTokenRepository verificationTokenRepository;

    private EmailServiceImpl emailService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, VerificationTokenRepository verificationTokenRepository, EmailServiceImpl emailService) {
        super();
        this.passwordEncoder=bCryptPasswordEncoder;
        this.verificationTokenRepository=verificationTokenRepository;
        this.emailService=emailService;
        this.userRepository = userRepository;
    }

    @Override
    public VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public void saveUser(UserDto user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User newUser=new User(user.getUsername(),user.getEmail(),encodedPassword,user.getFirstName(),user.getLastName(), GUIconstants.DEFAULT_PROFILE_PICTURE);
        System.out.println(newUser.toString());

        userRepository.save(newUser);
        VerificationToken verificationToken = this.generateVerificationToken(newUser);
        emailService.sendVerificationEmail(user.getEmail(),MailSubjects.STANDARD_ACTIVATE_ACCOUNT_SUBJECT,verificationToken.getToken());
    }
    @Override
    public void save(User user){
        userRepository.save(user);

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
