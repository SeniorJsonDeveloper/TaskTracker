package ru.taskapplication.security;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
public class AppUserPrincipal implements Principal {

    private final String username;

    private final String id;

    private final List<String> roles;

    public String getId() {
        return id;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String getName() {
        return username;
    }
}
