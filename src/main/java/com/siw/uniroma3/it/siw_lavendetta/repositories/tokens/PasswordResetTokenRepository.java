package com.siw.uniroma3.it.siw_lavendetta.repositories.tokens;

import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);

    public List<PasswordResetToken> getByUser(User user);

    PasswordResetToken save(PasswordResetToken passwordResetToken);

    void deleteById(Long id);

    List<PasswordResetToken> findByUser(User user);
}
