package com.asm.jit.service;

import com.asm.jit.service.dto.UserLocationByDateAndTimeReqDto;
import com.asm.jit.service.dto.UserLocationByDateAndTimeResDto;
import com.asm.jit.service.dto.UserLocationDto;

import java.util.Optional;

public interface UserLocationService {

    Optional<UserLocationDto> findOne(Long id);

    UserLocationDto createUserLocation(UserLocationDto userDto);

    UserLocationDto findLatestByUserId(String userId);

    UserLocationByDateAndTimeResDto getUserLocationByDateAndTime(UserLocationByDateAndTimeReqDto dto);

}
