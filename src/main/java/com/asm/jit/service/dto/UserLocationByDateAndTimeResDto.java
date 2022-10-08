package com.asm.jit.service.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserLocationByDateAndTimeResDto {

    private String userId;
    private List<LocationDto> locations;

}
