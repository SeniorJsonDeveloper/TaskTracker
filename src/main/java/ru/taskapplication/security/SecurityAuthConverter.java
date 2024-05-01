package ru.taskapplication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.taskapplication.security.jwt.TokenService;

@Component
@RequiredArgsConstructor
public class SecurityAuthConverter implements ServerAuthenticationConverter {

    private static final String BEARER_PREFIX = "Bearer ";


    private final TokenService tokenService;
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(this::extractBearerToken)
                .flatMap(token->{
                    if (tokenService.validateToken(token)){
                        return tokenService.toAuth(token);
                    }

                    return Mono.empty();
    });

    }
    private Mono<String> extractBearerToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION))
                .flatMap(t->{
                    if (t.startsWith(BEARER_PREFIX)) {
                        return Mono.just(t.substring(BEARER_PREFIX.length()));
                    }

                    return Mono.empty();
                });
    }
}
