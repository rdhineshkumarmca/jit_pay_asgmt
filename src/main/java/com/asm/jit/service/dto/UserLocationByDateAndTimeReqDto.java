package com.asm.jit.service.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserLocationByDateAndTimeReqDto {

    @NotNull
    private String userId;

    @NotNull
    private String fromDate;

    @NotNull
    private String toDate;

}
