package com.example.jwt.demo.domain;

import com.example.jwt.demo.domain.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
// @RequiredArgsConstructor
public class PostResponseDTO {
    public PostResponseDTO(PostEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
    private Long id;

    private String title;
    private String content;
}
