package com.example.jwt.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.demo.dao.AuthRepository;
import com.example.jwt.demo.domain.UserRequestDTO;
import com.example.jwt.demo.domain.UserResponseDTO;
import com.example.jwt.demo.domain.entity.UserEntity;
import com.example.jwt.demo.util.JwtProvider;

@Service
public class AuthService {
    @Autowired
    private AuthRepository repository;

    @Autowired
    private JwtProvider provider;

    public UserResponseDTO loginService(UserRequestDTO params){
        System.out.println("debug >> login service");
        String accToken = provider.generateAccToken(params.getEmail());
        String refToken = provider.generateReToken(params.getEmail());
        
        // 무조건 생성하는 코드
        // UserEntity entity = UserEntity.builder()
        //                     .email(params.getEmail())
        //                     .passwd(params.getPasswd())
        //                     .refreshToken(refToken)
        //                     .build();
        //             repository.save(entity);

        // DB에 존재하면 생성하지 않는 코드
        // select * from table where email = ?
        Optional<UserEntity> op = repository.findByEmail(params.getEmail());
        if (op.isPresent()) {
            UserEntity result = op.get();
            UserResponseDTO response = UserResponseDTO.builder()
                                        .email(result.getEmail())
                                        .passwd(result.getPasswd())
                                        .refreshToken(result.getRefreshToken())
                                        .accessToken(accToken)
                                        .build();
            return response;
        }else{
            UserEntity entity = UserEntity.builder()
                                .email(params.getEmail())
                                .passwd(params.getPasswd())
                                .refreshToken(refToken)
                                .build();
            repository.save(entity);
            //// 데이터를 심어주는 작업 새로 만들고 데이터를 담는다
            UserResponseDTO response = UserResponseDTO.builder()
                                .email(params.getEmail())
                                .passwd(params.getPasswd())
                                .refreshToken(refToken)
                                .accessToken(accToken)
                                .build();
            return response;
            
        }
        // return response;
    }

    public String renewService(String token) throws Exception{
        System.out.println("debug >> service renewService");
        Optional<UserEntity> op = repository.findByRefreshToken(token);
        if (op.isPresent()) {
            String email = provider.renewToken(op.get().getRefreshToken());
            String newToken = provider.generateAccToken(email);
            return newToken;
        }else{
            throw new Exception();
        }

    }
}
