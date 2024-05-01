package ru.taskapplication.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDto {

    private String id;

    private String username;

    private String email;

    private String role;
}
