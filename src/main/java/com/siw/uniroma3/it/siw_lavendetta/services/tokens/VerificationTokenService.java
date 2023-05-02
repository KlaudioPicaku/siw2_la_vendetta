package com.siw.uniroma3.it.siw_lavendetta.services.tokens;

import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;

public interface VerificationTokenService {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
    void saveOrUpdate(VerificationToken verificationToken);
    void delete(Long id);
}
