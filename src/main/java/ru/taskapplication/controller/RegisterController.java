package ru.taskapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.taskapplication.dto.user.UserInputDto;
import ru.taskapplication.entity.RoleType;
import ru.taskapplication.entity.UserEntity;
import ru.taskapplication.service.UserService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegisterController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> register(@RequestBody UserInputDto userInputDto) {
        return userService.createUser(UserEntity.builder()
                        .username(userInputDto.getUsername())
                        .email(userInputDto.getEmail())
                        .password(userInputDto.getPassword())
                        .roles(userInputDto.getRoles()
                                .stream()
                                .map(o-> RoleType.valueOf(o.toUpperCase()))
                                .collect(Collectors.toSet()))
                .build())
                .map(user->"You are successfully registered!");
    }
}
