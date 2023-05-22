package com.siw.uniroma3.it.siw_lavendetta.impl.token;

import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.PasswordResetToken;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import com.siw.uniroma3.it.siw_lavendetta.repositories.tokens.PasswordResetTokenRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.tokens.PasswordResetTokenService;
import com.siw.uniroma3.it.siw_lavendetta.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenImpl implements PasswordResetTokenService {
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;


    @Override
    public PasswordResetToken findByToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken.isValid()){
            return passwordResetToken;
        }
        return  null;
    }

    @Override
    public PasswordResetToken findByUser(User user) {
        List<PasswordResetToken> tokens=passwordResetTokenRepository.getByUser(user);
        for(PasswordResetToken t:tokens){
            if(t.isValid()){
                return  t;
            }
        }

        PasswordResetToken token=new PasswordResetToken();
        token.burn();
        return  token;
    }

    @Override
    public void saveOrUpdate(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public void delete(Long id) {
        passwordResetTokenRepository.deleteById(id);
    }

    @Override
    public PasswordResetToken generateResetToken(User user) {
        String token =  UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        token=token.toUpperCase();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public void burnByUser(User user) {
        List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findByUser(user);

        for(PasswordResetToken p: passwordResetTokens){
            p.burn();
        }
        passwordResetTokenRepository.saveAll(passwordResetTokens);

    }
}
