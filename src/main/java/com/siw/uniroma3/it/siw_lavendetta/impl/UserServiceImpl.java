package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.constants.GUIconstants;
import com.siw.uniroma3.it.siw_lavendetta.constants.MailSubjects;
import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.tokens.VerificationTokenRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.UserService;
import com.siw.uniroma3.it.siw_lavendetta.services.tokens.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private VerificationTokenRepository verificationTokenRepository;

    private EmailServiceImpl emailService;
    private PasswordResetTokenService passwordResetTokenService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           VerificationTokenRepository verificationTokenRepository,
                           EmailServiceImpl emailService, PasswordResetTokenService passwordResetTokenService) {
        super();
        this.passwordEncoder=bCryptPasswordEncoder;
        this.verificationTokenRepository=verificationTokenRepository;
        this.emailService=emailService;
        this.userRepository = userRepository;
        this.passwordResetTokenService=passwordResetTokenService;
    }

    @Override
    public VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public void saveUser(@org.jetbrains.annotations.NotNull UserDto user){
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

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void updateUser(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
        passwordResetTokenService.burnByUser(user);
    }

    @Override
    public boolean deletePreviousImage(User user) {
        boolean notDefaultPic= !(userRepository.findById(user.getId()).get().getImage().equals(GUIconstants.DEFAULT_PROFILE_PICTURE));

        if (notDefaultPic) {
            String absoluteImageUrl=user.getImage();
            String filePath="";
            if(absoluteImageUrl!=null || !absoluteImageUrl.isEmpty()){
                filePath=absoluteImageUrl;
            }
            if(!filePath.isEmpty()){
                String baseDirectory = System.getProperty("user.dir");
                Path path = Paths.get(baseDirectory,filePath);
//            System.out.println(path);

                try {
                    Files.delete(path);
                    System.out.println("File deleted successfully.");
                } catch (IOException e) {
                    System.out.println("Failed to delete the file: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean passwordMatch(User user, String inputPassword){
        if (user!=null){
            if(!inputPassword.isEmpty()){
                return passwordEncoder.matches(inputPassword,user.getPassword());
            }
        }
        return false;
    }


}
