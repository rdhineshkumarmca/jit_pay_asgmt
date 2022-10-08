package com.asm.jit.service.impl;

import com.asm.jit.config.Constants;
import com.asm.jit.model.User;
import com.asm.jit.model.UserLocation;
import com.asm.jit.repository.UserRepository;
import com.asm.jit.service.UserLocationService;
import com.asm.jit.service.UserService;
import com.asm.jit.service.dto.LocationDto;
import com.asm.jit.service.dto.UserDto;
import com.asm.jit.service.dto.UserLocationByDateAndTimeReqDto;
import com.asm.jit.service.dto.UserLocationDto;
import com.asm.jit.service.mapper.UserMapper;
import com.asm.jit.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserLocationService userLocationService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserLocationService userLocationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userLocationService = userLocationService;
    }

    @Override
    public Optional<UserDto> findOne(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setUserId(UUID.randomUUID().toString());
        user = userRepository.saveAndFlush(user);
        log.debug("Created Information for User: {}", user);
        return userMapper.toDto(user);
    }

    private User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestAlertException("Requested userID is not present in DB"));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = findByUserId(userDto.getUserId());

        if(StringUtils.isNotBlank(userDto.getFirstName()) && !StringUtils.equalsAnyIgnoreCase(userDto.getFirstName(), user.getFirstName())) {
            user.setFirstName(userDto.getFirstName());
        }
        if(StringUtils.isNotBlank(userDto.getSecondName()) && !StringUtils.equalsAnyIgnoreCase(userDto.getSecondName(), user.getSecondName())) {
            user.setSecondName(userDto.getSecondName());
        }
        if(StringUtils.isNotBlank(userDto.getEmail()) && !StringUtils.equalsAnyIgnoreCase(userDto.getEmail(), user.getEmail())) {
            user.setEmail(userDto.getEmail());
        }

        user = userRepository.saveAndFlush(user);
        log.debug("Created Information for User: {}", user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserWithRecentLocation(String userId) {
        return userRepository.findByUserId(userId).map(user -> {
            UserDto dto = userMapper.toDto(user);
            UserLocationDto userLocationDto = userLocationService.findLatestByUserId(user.getUserId());
            if(null != userLocationDto) {
                LocationDto locationDto = new LocationDto();
                locationDto.setLatitude(userLocationDto.getLatitude());
                locationDto.setLongitude(userLocationDto.getLongitude());
                dto.setLocation(locationDto);
            }
            return dto;
        }).orElseThrow(() -> new BadRequestAlertException("Requested userID is not present in DB"));
    }

}
