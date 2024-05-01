package ru.taskapplication.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tasks")
public class TaskEntity {

    @Id
    private String id;

    @Indexed
    private String title;

    @Indexed
    private String description;

    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    private Boolean completed;

    private String userId;

    private Mono<UserEntity> user;





}
