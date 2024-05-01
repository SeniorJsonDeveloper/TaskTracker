package ru.taskapplication.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskInputDto {

    private String title;

    private String description;

    private Boolean completed;

    private String userId;

    private Instant updatedAt;
}
