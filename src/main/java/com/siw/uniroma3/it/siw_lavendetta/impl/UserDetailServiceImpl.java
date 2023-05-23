package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.constants.GUIconstants;
import com.siw.uniroma3.it.siw_lavendetta.models.Provider;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    public Optional<User> loadByEmail(String email){
        return userRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,DisabledException{
        Optional<User> user= userRepository.findByUsername(username);
        if (!user.isPresent()){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.isPresent() && user.get().getRole()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if (user.isPresent() && !user.get().isEnabled()) {
            System.out.println("USER IS NOT ENABLED ! S K I P Z !");
            throw new DisabledException("Account is not enabled");
        }


        return  new org.springframework.security.core.userdetails.User(user.get().getUsername(),user.get().getPassword(),authorities);
    }

    public void processOAuthPostLogin(String username) {
        Optional<User> existUser=userRepository.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        if(!existUser.isPresent()){
            String randomEncodedPassword= passwordEncoder.encode(UUID.randomUUID().toString().replaceAll("-",""));

            User newUser=new User(username,username,randomEncodedPassword,username,username, GUIconstants.DEFAULT_PROFILE_PICTURE, Provider.GITHUB);
            newUser.setEnabled(true);

            userRepository.save(newUser);

        }

    }
}
