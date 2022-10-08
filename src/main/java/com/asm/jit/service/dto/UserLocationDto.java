package com.asm.jit.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLocationDto {

    private Long id;

    private String userId;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Instant createdOn;

    private Instant lastModifiedDate;

}
