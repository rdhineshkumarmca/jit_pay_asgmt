package com.asm.jit.service;

import com.asm.jit.service.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> findOne(Long id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto getUserWithRecentLocation(String userId);

}
