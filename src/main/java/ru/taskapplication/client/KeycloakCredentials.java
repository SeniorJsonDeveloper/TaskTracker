package ru.taskapplication.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.keycloak.client")
@Getter
@Setter
@Component
public class KeycloakCredentials {

    private String id;

    private String secret;
}
