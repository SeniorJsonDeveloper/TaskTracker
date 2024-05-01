package ru.taskapplication.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.taskapplication.entity.TaskEntity;

public interface TaskRepository extends ReactiveMongoRepository<TaskEntity, String>  {

    Mono<Boolean> existsByUserId(String userId);

    Mono<TaskEntity> findByUserId(String userId);

    Mono<TaskEntity> findByTitle(String title);
}
