package com.example.jwt.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.demo.dao.MemberRepository;
import com.example.jwt.demo.dao.PostRepository;
import com.example.jwt.demo.domain.MemberRequestDTO;
import com.example.jwt.demo.domain.PostRequestDTO;
import com.example.jwt.demo.domain.PostResponseDTO;
import com.example.jwt.demo.domain.UserRequestDTO;
import com.example.jwt.demo.domain.UserResponseDTO;
import com.example.jwt.demo.domain.entity.MemberEntity;
import com.example.jwt.demo.domain.entity.PostEntity;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

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

    /*
        -mybatis
        update table_name
        set title = ?, content = ?
        whete member_id = ? 
    */
    @Transactional
    public void updatePostService(String email, PostRequestDTO params){
        System.out.println("debug >> UserService updatePostService");
        System.out.println("debug >> execute query 1");
        MemberEntity member = memberRepository.findById(email)
                            .orElseThrow(() -> new RuntimeException("not found"));
        System.out.println("debug >> execute query 2");
        Optional<PostEntity> post = member.findPost(params.getId());
        System.out.println("debug >> execute query 3");
        post.get().updatePost(params);

    
    }

    public void deletePostService(String email, Long postId){
        System.out.println("debug >> UserService deletePostService");
        MemberEntity member = memberRepository.findById(email)
                            .orElseThrow(() -> new RuntimeException("not found"));
        PostEntity post = postRepository.findById(postId)
                            .orElseThrow(() -> new RuntimeException("not found post")); 
        postRepository.delete(post);
    }
}
