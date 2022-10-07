package com.asm.jit.service.impl;

import com.asm.jit.model.User;
import com.asm.jit.repository.UserRepository;
import com.asm.jit.service.UserService;
import com.asm.jit.service.dto.UserDto;
import com.asm.jit.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDto> findOne(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.saveAndFlush(user);
        log.debug("Created Information for User: {}", user);
        return userMapper.toDto(user);
    }
}
