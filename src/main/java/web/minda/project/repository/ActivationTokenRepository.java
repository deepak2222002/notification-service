package web.minda.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.minda.project.entity.ActivationToken;

public interface ActivationTokenRepository
        extends JpaRepository<ActivationToken, Long> {

    Optional<ActivationToken> findByToken(String token);
}