package ru.taskapplication.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Indexed
    private String id;

    @Indexed
    private String username;

    private String email;

    private String password;

    @Field(name = "roles")
    private Set<RoleType> roles = new HashSet<>();

    @Field(name = "tasks")
    private List<TaskEntity> tasks = new ArrayList<>();
}
