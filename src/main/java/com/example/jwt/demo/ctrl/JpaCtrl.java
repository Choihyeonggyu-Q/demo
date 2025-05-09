package com.example.jwt.demo.ctrl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.demo.dao.JpaSampleRepository;
import com.example.jwt.demo.domain.PostResponseDTO;
import com.example.jwt.demo.domain.dto.SampleResponseDTO;
import com.example.jwt.demo.domain.entity.JpaSampleEntity;
import com.example.jwt.demo.domain.entity.MemberEntity;
import com.example.jwt.demo.domain.entity.PostEntity;
import com.example.jwt.demo.util.JwtProvider;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




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
    @Transactional
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

    @PutMapping("/update")
    @Transactional
    public String update(@RequestBody Map<String, String> params) {
        System.out.println("debug >> JpaCtrl update");

        // JpaSampleEntity entity = jpaSampleRepository
        //                             .findById(params.get("userId"))
        //                             .orElseThrow(() -> new RuntimeException("not found"));
        // entity.setName(params.get("name"));
        // entity.setPasswd(params.get("passwd"));

        // JpaSampleEntity entity = JpaSampleEntity.builder()
        //                                         .userId(params.get("userId"))
        //                                         .passwd(params.get("passwd"))
        //                                         .name(params.get("name"))
        //                                         .point(Integer.parseInt(params.get("point")))
        //                                         .build();

        jpaSampleRepository.updateRow(params.get("userId"), params.get("passwd"), params.get("name"));
        return null;
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable("userId") String userId){
        System.out.println("debug >> delete");

        jpaSampleRepository.deleteRow(userId);
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<List<SampleResponseDTO>> list(@RequestParam Map<String, String> params) {
        System.out.println("debug >> list");

        // List<JpaSampleEntity> list = jpaSampleRepository.findAll();
        System.out.println("debug >> UserService getUserPostService");
        
        // List<SampleResponseDTO> result = list.stream()
        //                                     .map(SampleResponseDTO::new)
        //                                     .toList();

        List<SampleResponseDTO> result = jpaSampleRepository.findByAll();
        return ResponseEntity.ok().body(result);
    }
    
    
    
}
