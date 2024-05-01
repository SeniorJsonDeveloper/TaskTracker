package ru.taskapplication.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;
import ru.taskapplication.entity.UserEntity;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskOutDto {

    private String id;

    private String title;

    private String description;

    private Boolean completed;

    private String userId;

    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    private Mono<UserEntity> user;
}
