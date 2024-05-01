package ru.taskapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/moderator")
public class ModerController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public Mono<String> moder(){
        return Mono.just("Moderator");
    }
}
