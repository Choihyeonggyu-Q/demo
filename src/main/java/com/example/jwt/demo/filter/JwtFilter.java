package com.example.jwt.demo.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
브라우저 요청
get, put, get, delete 요청전에 자동으로 options(preflight request) 요청을 먼저 보냄
필터를 통해서 jwt 검사를 하게 되면 cors 에허
*/

@Component
public class JwtFilter implements Filter{

    // private final Key key = Keys.hmacShaKeyFor("Queue-key-vert-vert-vert-vert-hard-is-ok".getBytes(StandardCharsets.UTF_8));

    @Value("${jwt.secret}")
    private String secret;
    private Key key = null;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        System.out.println("debug >> filter");
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String path = req.getRequestURI();
        System.out.println("debug >> client path : "+path);
        
        String method = req.getMethod();
        System.out.println("debug >> client method : "+method);
        if ("OPTIONS".equalsIgnoreCase(method)) {
            res.setStatus(HttpServletResponse.SC_OK);
            // res.setHeader("Access-Control-Allow-Origin", "*");
            // res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            // res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            chain.doFilter(request, response);
            return;
        }

        if (isPassPath(path)) {
            System.out.println("debug >> 인증이 필요없는 경로는 필터를 통과: ");
            chain.doFilter(request, response);
            return ;
        }

        
        // Authorization 헤더 확인
        String authHeader = req.getHeader("Authorization");
        System.out.println("debug >> authHeader : "+authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("debug >> if true Authorization 헤더 확인");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ;
        }
        String token = authHeader.substring(7);
        System.out.println("debug >> token : "+token);

        try{    
            // Jws 서명 + 유효기간 체크
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            System.out.println("debug >> 검증 성공 -> 컨트롤러");
            chain.doFilter(request, response);
        }catch(Exception e){
            System.out.println("토큰 문제가 발생");
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("Invalid or expired token"); 
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

            chain.doFilter(request, response);
            return ;
        }
    }
    
    

    // 특정한 URL은 검증없이 CONTROLLER로 넘긴다
    public boolean isPassPath(String path){
        return  path.startsWith("/swagger-ui")  ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/h2-console")  ||
                path.startsWith("/auth/login")  ||
                path.startsWith("/auth/renew")  ||
                path.startsWith("/jpa")         ||
                path.startsWith("/forcast")     ||
                path.startsWith("/user");

    }
    
}
