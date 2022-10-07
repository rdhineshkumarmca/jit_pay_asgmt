package com.asm.jit.service.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class LocationDto {

    private BigDecimal longitude;
    private BigDecimal latitude;

}
