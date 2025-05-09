package com.example.jwt.demo.openapi.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForcastRequestDTO {

    @NotNull(message = "형식) 1")
    private String beach_num;
    @NotNull(message = "형식) 20220622")
    private String base_date;
    @NotNull(message = "형식) 1100")
    private String base_time;

}

