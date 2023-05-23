package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Optional;

public interface UserService {
    VerificationToken generateVerificationToken(User user);
    Optional<User> getUserByUsername(String username);
    void saveUser(UserDto user);

    void save(User user);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);

    void updateUser(User user, String password);

    boolean deletePreviousImage(User user);

    void processOAuth2User(OAuth2AuthenticationToken authenticationToken);
}