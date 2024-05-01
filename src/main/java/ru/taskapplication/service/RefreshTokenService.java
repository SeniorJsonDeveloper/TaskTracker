package ru.taskapplication.service;

import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import reactor.core.publisher.Mono;
import ru.taskapplication.entity.RefreshTokenEntity;

public interface RefreshTokenService {

    Mono<RefreshTokenEntity> save(String userId);

    Mono<RefreshTokenEntity> findByValue(String token);
}
