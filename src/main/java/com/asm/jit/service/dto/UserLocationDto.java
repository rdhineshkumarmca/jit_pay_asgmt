package com.asm.jit.service.dto;

import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@ToString
public class UserLocationDto {

    private Instant createdOn;

    private LocationDto location;

}
