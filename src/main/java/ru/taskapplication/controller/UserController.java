package ru.taskapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/user")
public class UserController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Mono<String> user(){
        return Mono.just("User");
    }
}
