package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
