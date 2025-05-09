package com.example.jwt.demo.domain.dto;

import com.example.jwt.demo.domain.entity.JpaSampleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class SampleResponseDTO {

    public SampleResponseDTO(JpaSampleEntity entity) {
        this.name = entity.getName();
        this.userId = entity.getUserId();
        this.passwd = entity.getPasswd();
    }

    public SampleResponseDTO(String userId, String passwd, String name) {
        this.name = name;
        this.userId = userId;
        this.passwd = passwd;
    }

    private String userId;
    private String passwd;
    private String name;
    private String point;
    private String refreshToken;
}
