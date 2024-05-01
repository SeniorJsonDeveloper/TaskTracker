package ru.taskapplication.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.taskapplication.dto.task.TaskInputDto;
import ru.taskapplication.dto.task.TaskOutDto;

import java.util.List;

public interface TaskService {

    Flux<List<TaskOutDto>> getAll();

    Mono<TaskOutDto> getById(String id);

    Mono<TaskOutDto> add(TaskInputDto taskOutDto);

    Mono<TaskOutDto> update(String id,TaskInputDto taskOutDto);

    Mono<Void> deleteById(String id);

}
