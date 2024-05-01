package ru.taskapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.taskapplication.entity.RefreshTokenEntity;
import ru.taskapplication.exception.RefreshTokenException;
import ru.taskapplication.repository.RefreshTokenRepository;
import ru.taskapplication.service.RefreshTokenService;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwt.refreshTokenExpire}")
    private Duration refreshTokenExpire;

    @Override
    public Mono<RefreshTokenEntity> save(String userId) {
        String tokenValue = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        var token = new RefreshTokenEntity(id,userId,tokenValue);
        return refreshTokenRepository.save(token,refreshTokenExpire)
                .filter(is->is)
                .flatMap(it->Mono.just(token))
                .switchIfEmpty(Mono.error(new RefreshTokenException("Error on save refresh token for userId: " + userId)));

    }

    @Override
    public Mono<RefreshTokenEntity> findByValue(String token) {
        return refreshTokenRepository.findByTokenValue(token);
    }
}
