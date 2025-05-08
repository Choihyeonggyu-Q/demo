package com.example.jwt.demo.domain.entity;

import com.example.jwt.demo.domain.PostRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
데이터 베이스 테이블과 java 객체간의 매핑을 도와주는
@Table
@Entity - 반드시 기본키(@id)를 지정해야함 
*/

@Table(name = "siat_post_tbl")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    //oracle version
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "post_seq_gen")
    @SequenceGenerator( name = "post_seq_gen", 
                        sequenceName = "post_seq", 
                        allocationSize = 1)
    private Long    id;

    private String title;
    private String content;

    public void updatePost(PostRequestDTO params){
        this.title = params.getTitle();
        this.content = params.getContent();
    }
}
