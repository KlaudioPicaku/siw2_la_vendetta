package com.siw.uniroma3.it.siw_lavendetta.impl.token;

import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import com.siw.uniroma3.it.siw_lavendetta.repositories.tokens.VerificationTokenRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken findByToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isValid()){
            return verificationToken;
        }
        return  null;
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.getByUser(user);
    }

    @Override
    public void saveOrUpdate(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void delete(Long id) {
        verificationTokenRepository.deleteById(id);

    }

}
