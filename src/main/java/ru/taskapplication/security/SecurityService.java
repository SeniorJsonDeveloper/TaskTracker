package ru.taskapplication.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.taskapplication.dto.token.TokenDto;
import ru.taskapplication.entity.UserEntity;
import ru.taskapplication.exception.PasswordException;
import ru.taskapplication.security.jwt.TokenService;
import ru.taskapplication.service.RefreshTokenService;
import ru.taskapplication.service.UserService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    public Mono<TokenDto> processPasswordToken(String username,String password){
        return userService.findByUsername(username)
                .flatMap(user->{
                    if(!passwordEncoder.matches(password,user.getPassword())){
                        log.error("Bad trying to check password for user: {}",username);
                        return Mono.error(new PasswordException());
                    }
                    return createToken(user);
                });
    }



    public Mono<TokenDto> processRefreshToken(String refreshToken){
        return refreshTokenService.findByValue(refreshToken)
                .flatMap(r->userService.findById(r.getId()))
                .flatMap(this::createToken);
    }

    public Mono<TokenDto> createToken(UserEntity user){
        String token = tokenService.generateToken(
                user.getUsername(),
                user.getId(),
                user.getRoles().stream().map(Enum::name).collect(Collectors.toList())
        );
        return refreshTokenService.save(user.getId())
                .flatMap(r->Mono.just(new TokenDto(token, r.getRefreshTokenValue())));
    }
}
