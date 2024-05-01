package ru.taskapplication.repository;

import reactor.core.publisher.Mono;
import ru.taskapplication.entity.RefreshTokenEntity;

import java.time.Duration;

public interface RefreshTokenRepository {

    Mono<Boolean> save(RefreshTokenEntity refreshTokenEntity, Duration duration);

    Mono<RefreshTokenEntity> findByTokenValue(String token);
}
