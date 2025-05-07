package com.example.jwt.demo.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.demo.domain.MemberRequestDTO;
import com.example.jwt.demo.domain.PostRequestDTO;
import com.example.jwt.demo.domain.PostResponseDTO;
import com.example.jwt.demo.domain.UserRequestDTO;
import com.example.jwt.demo.service.UserService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Member;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/user")
public class UserCtrl {
    
    @Autowired
    private UserService service;

    @PostMapping("/usercreate")
    public ResponseEntity<Void> createUser(@RequestBody MemberRequestDTO params ) {
        System.out.println("debug >> UserCtrl createUser");
        service.createUserService(params);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/post")
    public ResponseEntity<PostResponseDTO> createPost(@PathVariable(name = "id")String id, @RequestBody PostRequestDTO params) {
        System.out.println("debug >> UserCtrl createPost");
        PostResponseDTO response = service.createPostService(id, params);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}/list")
    public ResponseEntity getUserPosts(@PathVariable("id")String id) {
        System.out.println("debug >> UserCtrl getUserPosts");
        List<PostResponseDTO> list = service.getUserPostsService(id);
        return ResponseEntity.ok().body(list);
    }
    
}
