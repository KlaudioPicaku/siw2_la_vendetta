package com.siw.uniroma3.it.siw_lavendetta.repositories.tokens;

import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    public VerificationToken getByUser(User user);

}
