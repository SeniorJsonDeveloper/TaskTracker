package ru.taskapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity implements Serializable {

    private String id;

    private String userId;

    private String refreshTokenValue;
}
