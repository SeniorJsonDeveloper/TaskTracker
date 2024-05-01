package ru.taskapplication.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.taskapplication.dto.task.TaskInputDto;
import ru.taskapplication.dto.task.TaskOutDto;
import ru.taskapplication.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MODERATOR') or ('ROLE_ADMIN') or ('ROLE_USER')")
    public Flux<List<TaskOutDto>> getTasks() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') ('ROLE_ADMIN')")
    public Mono<TaskOutDto> getTaskById(@PathVariable String id) {
        return taskService.getById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR') ('ROLE_ADMIN')")
    public Mono<TaskOutDto> addTask(@RequestBody TaskInputDto taskInputDto){
        return taskService.add(taskInputDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') ('ROLE_ADMIN')")
    public Mono<TaskOutDto> updateTask(@PathVariable String id, @RequestBody TaskInputDto taskInputDto){
        return taskService.update(id,taskInputDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') ('ROLE_ADMIN')")
    public Mono<Void> deleteTask(@PathVariable String id){
        return taskService.deleteById(id);
    }
}
