package com.example.jwt.demo.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.demo.domain.UserRequestDTO;
import com.example.jwt.demo.domain.UserResponseDTO;
import com.example.jwt.demo.service.AuthService;

// import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
public class AuthCtrl {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO params){
        System.out.println("debug >> login controller");
        System.out.println("debug >> login controller params : "+params );
        UserResponseDTO response = service.loginService(params);
        System.out.println("debug >> login controller result: " +response);
        return ResponseEntity.ok()
                        .header("Authorization", "Bearer "+response.getAccessToken())
                        .header("Refresh-Token", response.getRefreshToken())
                        .body(response);
    }
}
