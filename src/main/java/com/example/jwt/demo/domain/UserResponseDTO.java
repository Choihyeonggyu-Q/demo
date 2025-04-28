package com.example.jwt.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserResponseDTO {
    private Long id;

    private String email;
    private String passwd;

    private String accessToken;
    private String refreshToken;
}
