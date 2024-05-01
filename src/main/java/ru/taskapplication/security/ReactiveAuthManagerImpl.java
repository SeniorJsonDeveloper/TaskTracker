package ru.taskapplication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.taskapplication.exception.AuthException;

@Component
@RequiredArgsConstructor
public class ReactiveAuthManagerImpl implements ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService reactiveUserDetails;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
        return reactiveUserDetails.findByUsername(principal.getName())
                .filter(UserDetails::isEnabled)
                .switchIfEmpty(Mono.error(new AuthException("Username disabled!")))
                .map(user-> authentication);
    }
}
