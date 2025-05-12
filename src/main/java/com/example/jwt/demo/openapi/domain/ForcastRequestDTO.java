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

    @NotNull(message = "형식) TMP")
    private String catrgory;
    @NotNull(message = "형식) 20250509")
    private String fcstDate;
    @NotNull(message = "형식) 1200")
    private String fcstTime;
    @NotNull(message = "형식) 0.5")
    private String fcstValue;
    @NotNull(message = "형식) 49")
    private String nx;
    @NotNull(message = "형식) 124")
    private String ny;


}

