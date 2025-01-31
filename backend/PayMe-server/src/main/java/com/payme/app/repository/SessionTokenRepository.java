package com.payme.app.repository;

import com.payme.app.authentication.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionTokenRepository extends JpaRepository<SessionToken, UUID> {
    void deleteByToken(String token);

    Optional<SessionToken> findByToken(String token);
}
