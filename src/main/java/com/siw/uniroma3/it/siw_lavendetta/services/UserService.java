package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.User;

public interface UserService {
    User getUserByUsername(String username);
    void saveUser(User user);
}