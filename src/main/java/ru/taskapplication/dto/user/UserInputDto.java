package ru.taskapplication.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {

    private String username;

    private String password;

    private String email;

    private Set<String> roles = new HashSet<>();
}
