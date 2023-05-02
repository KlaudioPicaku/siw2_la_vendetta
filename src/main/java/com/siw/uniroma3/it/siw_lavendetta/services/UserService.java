package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;

public interface UserService {
    VerificationToken generateVerificationToken(User user);
    User getUserByUsername(String username);
    void saveUser(UserDto user);

    void save(User user);
}