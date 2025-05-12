package com.example.jwt.demo.openapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jwt.demo.openapi.domain.ForcastResponseDTO;
import com.example.jwt.demo.openapi.util.ForcastItems;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ForcastService {
    
    public List<ForcastResponseDTO> parsingJson(String str){
        System.out.println("debug >> service parsingJson");
        ObjectMapper mapper = new ObjectMapper();
        List<ForcastResponseDTO> list = null;
        try {
            ForcastItems items = mapper.readValue(str, ForcastItems.class);
            list = items.getItems();
            System.out.println("debug >> list size"+list.size());
            list.stream()
                .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 구현

        return list;
    }
}
