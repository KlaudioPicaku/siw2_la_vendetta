package com.siw.uniroma3.it.siw_lavendetta.services.tokens;

import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.PasswordResetToken;


public interface PasswordResetTokenService {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
    void saveOrUpdate(PasswordResetToken passwordResetToken);
    void delete(Long id);

    PasswordResetToken generateResetToken(User user);

    void burnByUser(User user);
}
