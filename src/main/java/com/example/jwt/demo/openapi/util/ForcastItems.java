package com.example.jwt.demo.openapi.util;

import java.util.Arrays;
import java.util.List;

import com.example.jwt.demo.openapi.domain.ForcastResponseDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class ForcastItems {
    
    @JsonProperty("item")
    private List<ForcastResponseDTO> items;

    @JsonCreator
    public ForcastItems(@JsonProperty("response") JsonNode node){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode item = node.findValue("item");
            ForcastResponseDTO[] ary = mapper.treeToValue(item, ForcastResponseDTO[].class);
            items = Arrays.asList(ary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
