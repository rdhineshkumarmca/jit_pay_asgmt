package com.asm.jit.web.rest;

import com.asm.jit.service.UserLocationService;
import com.asm.jit.service.dto.UserDto;
import com.asm.jit.service.dto.UserLocationDto;
import com.asm.jit.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserLocationResource {

    private final Logger log = LoggerFactory.getLogger(UserLocationResource.class);

    private final UserLocationService userLocationService;

    public UserLocationResource(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    @GetMapping("/user/location/{id}")
    public ResponseEntity<UserLocationDto> getUserById(@PathVariable Long id) {
        log.debug("REST request to get user location by ID:{}", id);
        Optional<UserLocationDto> dto = userLocationService.findOne(id);
        if(dto.isPresent()) {
            return ResponseEntity.ok().body(dto.get());
        } else {
            log.error("Requested ID not present in DB, hence throwing error");
            throw new BadRequestAlertException("Unable to find the requested ID");
        }
    }

    @PostMapping("/user/location")
    public ResponseEntity<UserLocationDto> createUser(@Valid @RequestBody UserLocationDto userLocationDto) throws URISyntaxException {
        log.debug("REST request to save User Location : {}", userLocationDto);
        if(userLocationDto.getId() != null) {
            log.error("Requested user location already have an ID, hence throwing error");
            throw new BadRequestAlertException("A new user location cannot already have an ID");
        } else {
            UserLocationDto newUser = userLocationService.createUserLocation(userLocationDto);
            return ResponseEntity.created(new URI("/api/user/location/" + newUser.getId()))
                    .body(newUser);
        }
    }
}
