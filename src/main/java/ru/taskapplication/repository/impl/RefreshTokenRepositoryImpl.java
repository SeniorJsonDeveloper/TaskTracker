package ru.taskapplication.repository.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.taskapplication.entity.RefreshTokenEntity;
import ru.taskapplication.exception.RefreshTokenException;
import ru.taskapplication.repository.RefreshTokenRepository;

import java.time.Duration;

@Repository
@Slf4j
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private static final String REFRESH_TOKEN_INDEX = "refreshTokenIndex";

    private final ReactiveValueOperations<String,RefreshTokenEntity> operationsForValue;

    private final ReactiveHashOperations<String,String,String> operationsForHash;

    public RefreshTokenRepositoryImpl(ReactiveRedisTemplate<String, RefreshTokenEntity> template){
        operationsForValue = template.opsForValue();
        operationsForHash = template.opsForHash();
    }
    @Override
    public Mono<Boolean> save(RefreshTokenEntity refreshTokenEntity, Duration duration) {
        return operationsForValue.set(refreshTokenEntity.getId(),refreshTokenEntity,duration)
                .then(operationsForHash.put(REFRESH_TOKEN_INDEX,refreshTokenEntity.getRefreshTokenValue(),
                        refreshTokenEntity.getId()));

    }

    @Override
    public Mono<RefreshTokenEntity> findByTokenValue(String token) {
        return operationsForHash.get(REFRESH_TOKEN_INDEX,token)
                .flatMap(r->operationsForHash.remove(REFRESH_TOKEN_INDEX,token)
                        .flatMap(c->{
                            log.info("Cleaning refreshToken: {}", c);
                            return operationsForValue.get(r);
                        }))
                .switchIfEmpty(Mono.error(new RefreshTokenException("Refresh token not found: " + token)));
    }
}
