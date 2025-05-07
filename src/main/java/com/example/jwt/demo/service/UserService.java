package com.example.jwt.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.demo.dao.MemberRepository;
import com.example.jwt.demo.domain.MemberRequestDTO;
import com.example.jwt.demo.domain.PostRequestDTO;
import com.example.jwt.demo.domain.PostResponseDTO;
import com.example.jwt.demo.domain.UserRequestDTO;
import com.example.jwt.demo.domain.UserResponseDTO;
import com.example.jwt.demo.domain.entity.MemberEntity;
import com.example.jwt.demo.domain.entity.PostEntity;

@Service
public class UserService {

    @Autowired
    private MemberRepository memberRepository;

    public void createUserService(MemberRequestDTO params){
        System.out.println("debug >> UserService createUserService");
        MemberEntity entity = MemberEntity.builder()
                                    .email(params.getEmail())
                                    .passwd(params.getPasswd())
                                    .build();
         // 데이터베이스에 저장
         memberRepository.save(entity);
        
         System.out.println("debug >> User created successfully: " + entity.getEmail());
    }

    public PostResponseDTO createPostService(String id, PostRequestDTO params){
        System.out.println("debug >> UserService createPostService");
        Optional<MemberEntity> op = memberRepository.findById(id);
        PostEntity post = PostEntity.builder()
                                    .title(params.getTitle())
                                    .content(params.getContent())
                                    .build();
        if (op.isPresent()) {
            op.get().addPost(post);
            memberRepository.save(op.get());
            return PostResponseDTO.builder()
                                .title(params.getTitle())
                                .content(params.getContent())
                                .build();
        }

        return null;
    }

    public List<PostResponseDTO> getUserPostsService(String id){
        System.out.println("debug >> UserService getUserPostService");
        Optional<MemberEntity> op = memberRepository.findById(id);
        if (op.isPresent()) {
            List<PostEntity> posts = op.get().getPosts();
            return posts.stream()
                .map(PostResponseDTO::new)
                .toList();
        }
        return null;
    }
}
