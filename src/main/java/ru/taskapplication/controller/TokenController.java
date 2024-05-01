package ru.taskapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.taskapplication.dto.LoginDto;
import ru.taskapplication.dto.token.RefreshTokenDto;
import ru.taskapplication.dto.token.TokenDto;
import ru.taskapplication.security.SecurityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/token")
public class TokenController {

    private final SecurityService securityService;

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TokenDto> createToken(@RequestBody LoginDto loginDto) {
        return securityService.processPasswordToken(loginDto.getPassword(), loginDto.getUsername())
                .map(o-> new TokenDto(o.getToken(),o.getRefreshToken()));
    }

    @PostMapping("/refreshToken")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TokenDto> createRefreshToken(@RequestBody RefreshTokenDto refreshTokenDto){
        return securityService.processRefreshToken(refreshTokenDto.getToken())
                .map(o-> new TokenDto(o.getToken(),o.getRefreshToken()));
    }


}
