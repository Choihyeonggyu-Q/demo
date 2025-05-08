package com.example.jwt.demo.ctrl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.demo.dao.JpaSampleRepository;
import com.example.jwt.demo.domain.entity.JpaSampleEntity;
import com.example.jwt.demo.util.JwtProvider;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/jpa")
public class JpaCtrl {
    @Autowired
    private JpaSampleRepository jpaSampleRepository;

    @Autowired
    private JwtProvider provider;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody Map<String, String> params) {
        System.out.println("debug >> signUp");

        JpaSampleEntity entity = JpaSampleEntity.builder()
                                                .userId(params.get("userId"))
                                                .passwd(params.get("passwd"))
                                                .name(params.get("name"))
                                                .point(Integer.parseInt(params.get("point")))
                                                .build();
        jpaSampleRepository.save(entity);
        return null;
    }

    @GetMapping("/sign-in")
    public String signIn(@RequestBody Map<String, String> params) {
        // token 생성
        System.out.println("debug >> JpaCtrl signIn");
        String accToken = provider.generateAccToken(params.get("userId"));
        String refToken = provider.generateReToken(params.get("userId"));
        JpaSampleEntity entity = jpaSampleRepository
                .findByUserIdAndPasswd(params.get("userId"), params.get("passwd"))
                .orElseThrow(() -> new RuntimeException("not found"));
        entity.setRefreshToken(refToken);
        return null;
    }
    
    
}
