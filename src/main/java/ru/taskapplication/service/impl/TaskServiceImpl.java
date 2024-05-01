package ru.taskapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.taskapplication.dto.task.TaskInputDto;
import ru.taskapplication.dto.task.TaskOutDto;
import ru.taskapplication.entity.TaskEntity;
import ru.taskapplication.exception.NotFoundException;
import ru.taskapplication.mapper.TaskMapper;
import ru.taskapplication.mapper.UserMapper;
import ru.taskapplication.repository.TaskRepository;
import ru.taskapplication.service.TaskService;
import ru.taskapplication.service.UserService;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    private final TaskMapper taskMapper;


    @Override
    public Flux<List<TaskOutDto>> getAll() {
        return Flux.just(taskMapper.toListDtoFromFlux(taskRepository.findAll()));
    }

    @Override
    public Mono<TaskOutDto> getById(String id) {
        return Mono.just(taskMapper.toMonoDto(taskRepository.findById(id)));
    }

    @Override
    @Transactional
    public Mono<TaskOutDto> add(TaskInputDto inputDto) {
        TaskEntity task = new TaskEntity();
        task.setId(UUID.randomUUID().toString());
        task.setTitle(inputDto.getTitle());
        task.setDescription(inputDto.getDescription());
        task.setCompleted(false);
        task.setCreatedAt(Instant.now());
        task.setUser(userService.findById(inputDto.getUserId()));
        taskRepository.save(task);
        log.info("Creating new task: {}", task);
        TaskOutDto taskOutDto = new TaskOutDto();
        taskOutDto.setId(task.getId());
        taskOutDto.setTitle(task.getTitle());
        taskOutDto.setDescription(task.getDescription());
        taskOutDto.setCompleted(task.getCompleted());
        taskOutDto.setCreatedAt(Instant.now());
        taskOutDto.setUpdatedAt(task.getUpdatedAt());
        taskOutDto.setUser(task.getUser());
        return Mono.just(taskOutDto);

    }

    @Override
    @Transactional
    public Mono<TaskOutDto> update(String id, TaskInputDto inputDto) {
        TaskEntity task = taskMapper.toDto(getById(id));
        if (!task.getTitle().equals(inputDto.getTitle())) {
            task.setTitle(inputDto.getTitle());
        }
        if (!task.getDescription().equals(inputDto.getDescription())) {
            task.setDescription(inputDto.getDescription());
        }
        if (!task.getCompleted().equals(inputDto.getCompleted())) {
            task.setCompleted(inputDto.getCompleted());
        }

        return Mono.just(taskMapper.toMonoDto(taskRepository.save(task)));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        TaskEntity task = taskMapper.toDto(getById(id));
        if (task == null) {
            throw new NotFoundException(MessageFormat.format("Task with id {0} not found", id));
        }
        return taskRepository.deleteById(id);
    }
}
