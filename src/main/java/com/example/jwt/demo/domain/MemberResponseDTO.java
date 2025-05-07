package com.example.jwt.demo.domain;

import java.util.List;

import com.example.jwt.demo.domain.entity.PostEntity;

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
public class MemberResponseDTO {
    
    private Long id;

    private String email;
    private String passwd;

    private String accessToken;
    private String refreshToken;

    //post
    private List<PostResponseDTO> post;
}
