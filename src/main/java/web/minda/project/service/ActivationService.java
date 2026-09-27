package web.minda.project.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import web.minda.project.entity.ActivationToken;
import web.minda.project.repository.ActivationTokenRepository;

@Service
public class ActivationService {

    private final ActivationTokenRepository tokenRepository;

    public ActivationService(
            ActivationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String createActivationToken(String email) {

        String token = UUID.randomUUID().toString();

        ActivationToken activationToken =
                new ActivationToken();

        activationToken.setToken(token);
        activationToken.setEmail(email);

        activationToken.setExpiresAt(
                LocalDateTime.now().plusHours(24)
        );

        activationToken.setUsed(false);

        tokenRepository.save(activationToken);

        return token;
    }
}