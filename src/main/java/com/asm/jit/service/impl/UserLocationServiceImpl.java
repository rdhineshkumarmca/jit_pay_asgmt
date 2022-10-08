package com.asm.jit.service.impl;

import com.asm.jit.config.Constants;
import com.asm.jit.model.User;
import com.asm.jit.model.UserLocation;
import com.asm.jit.repository.UserLocationRepository;
import com.asm.jit.repository.UserRepository;
import com.asm.jit.service.UserLocationService;
import com.asm.jit.service.dto.LocationDto;
import com.asm.jit.service.dto.UserLocationByDateAndTimeReqDto;
import com.asm.jit.service.dto.UserLocationByDateAndTimeResDto;
import com.asm.jit.service.dto.UserLocationDto;
import com.asm.jit.service.mapper.UserLocationMapper;
import com.asm.jit.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserLocationServiceImpl implements UserLocationService {

    private final Logger log = LoggerFactory.getLogger(UserLocationServiceImpl.class);

    private final UserLocationRepository userLocationRepository;

    private final UserLocationMapper userLocationMapper;

    private final UserRepository userRepository;

    public UserLocationServiceImpl(UserLocationRepository userLocationRepository, UserLocationMapper userLocationMapper, UserRepository userRepository) {
        this.userLocationRepository = userLocationRepository;
        this.userLocationMapper = userLocationMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserLocationDto> findOne(Long id) {
        return userLocationRepository.findById(id)
                .map(userLocationMapper::toDto);
    }

    @Override
    public UserLocationDto createUserLocation(UserLocationDto userLocationDto) {
        if(userRepository.findByUserId(userLocationDto.getUserId()).isPresent()) {
            if(!userLocationRepository.existsByLongitudeAndLatitude(userLocationDto.getLongitude(),userLocationDto.getLatitude())) {
                UserLocation userLocation = userLocationMapper.toEntity(userLocationDto);
                userLocation = userLocationRepository.saveAndFlush(userLocation);
                log.debug("Created Information for UserLocation: {}", userLocation);
                return userLocationMapper.toDto(userLocation);
            } else {
                throw new BadRequestAlertException("Requested Longitude and Latitude is already present in DB");
            }
        } else {
            log.error("Requested User ID not present in DB, hence throwing error");
            throw new BadRequestAlertException("Requested userID is not present in DB");
        }
    }

    @Override
    public UserLocationDto findLatestByUserId(String userId) {
        Optional<UserLocation> userLocation = userLocationRepository.findLatestByUserId(userId);
        if(userLocation.isPresent()) {
            return userLocationMapper.toDto(userLocation.get());
        } else {
            log.info("Uer Location is not found wrt requested userId({})", userId);
            return null;
        }
    }

    @Override
    public UserLocationByDateAndTimeResDto getUserLocationByDateAndTime(UserLocationByDateAndTimeReqDto dto) {
        UserLocationByDateAndTimeResDto resDto = null;
        if(userRepository.findByUserId(dto.getUserId()).isPresent()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME).withZone(ZoneId.systemDefault());
            List<LocationDto> locations = new ArrayList<>();
            userLocationRepository.findByDateAndTimeBetween(Instant
                    .from(dateTimeFormatter.parse(dto.getFromDate())), Instant.from(dateTimeFormatter.parse(dto.getToDate()))).forEach(userLocations -> {
                LocationDto locationDto = new LocationDto();
                locationDto.setLongitude(userLocations.getLongitude());
                locationDto.setLatitude(userLocations.getLatitude());
                locationDto.setCreatedOn(userLocations.getCreatedOn());
                locations.add(locationDto);
            });
            resDto = new UserLocationByDateAndTimeResDto();
            resDto.setUserId(dto.getUserId());
            resDto.setLocations(locations);
        } else {
            log.error("Requested User ID not present in DB, hence throwing error");
            throw new BadRequestAlertException("Requested userID is not present in DB");
        }
        return resDto;
    }

}
