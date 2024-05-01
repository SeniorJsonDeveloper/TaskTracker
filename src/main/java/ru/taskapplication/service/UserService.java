package ru.taskapplication.service;

import reactor.core.publisher.Mono;
import ru.taskapplication.entity.UserEntity;

public interface UserService {

    Mono<UserEntity> createUser(UserEntity user);

    Mono<UserEntity> findByUsername(String username);

    Mono<UserEntity> findById(String id);

}
