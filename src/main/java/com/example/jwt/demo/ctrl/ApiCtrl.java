package com.example.jwt.demo.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class ApiCtrl {
    
    @GetMapping("/hello")
    public String hello(){
        System.out.println("debug >> ApiCtrl hello");
        return "Hello, Siat";
        
    }
}
