package ru.taskapplication.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.taskapplication.exception.TokenParsingException;
import ru.taskapplication.security.AppUserPrincipal;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private static final String ROLE_CLAIM = "role";

    private static final String ID_CLAIM = "id";

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.tokenExpire}")
    private Duration tokenExpire;

    public String generateToken(String username, String id, List<String> roles) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+tokenExpire.toMillis()))
                .claim(ROLE_CLAIM,roles)
                .claim(ID_CLAIM,id)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public Mono<Authentication> toAuth(String token){
        var tokenBody = getBody(token);
        var subject = tokenBody.getSubject();
        var id = tokenBody.get(ID_CLAIM,String.class);
        List<String> roles = (List<String>)tokenBody.get(ROLE_CLAIM);
        if (Objects.isNull(subject) || Objects.isNull(id) || Objects.isNull(roles)) {
            log.error("Subject,tole or id is null. Subject: {}; Roles: {}; ID: {}", subject, roles,id);
            throw new TokenParsingException("Subject , roles, and ID must be not null");
        }
        if (subject.isBlank()||roles.isEmpty()||id.isBlank()){
            log.error("Subject, role or ID is empty. Subject: {}; Roles: {}; ID:{}", subject, roles,id);
            throw new TokenParsingException("Subject, roles or ID must be not empty");
        }
        var principal = new AppUserPrincipal(subject,id,roles);

        var auth = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
        return Mono.just(auth);
    }


    public Claims getBody(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }
}
