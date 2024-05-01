package ru.taskapplication.client;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakCredentials.class)
public class KeycloakClient {

    @Bean
    public Keycloak keycloak(@Value("${spring.app.keycloak.url}") String url,
                             @Value("${spring.app.keycloak.realm}") String realm,
            KeycloakCredentials keycloakCredentials) {
        return KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakCredentials.getId())
                .clientSecret(keycloakCredentials.getSecret())
                .build();
    }
}
