package com.asm.jit.web.rest;

import com.asm.jit.service.UserService;
import com.asm.jit.service.dto.UserDto;
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
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.debug("REST request to get user by userID:{}", id);
        Optional<UserDto> dto = userService.findOne(id);
        if(dto.isPresent()) {
            return ResponseEntity.ok().body(dto.get());
        } else {
            throw new BadRequestAlertException("Unable to find the requested ID");
        }
    }

    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);
        if(userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID");
        } else {
            UserDto newUser = userService.createUser(userDTO);
            return ResponseEntity.created(new URI("/api/user/" + newUser.getId()))
                .body(newUser);
        }
    }

    @PutMapping("/user")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDTO) throws URISyntaxException {
        log.debug("REST request to update User : {}", userDTO);
        if(userDTO.getUserId() == null) {
            throw new BadRequestAlertException("Update User cannot empty userID");
        } else {
            UserDto newUser = userService.updateUser(userDTO);
            return ResponseEntity.created(new URI("/api/user/" + newUser.getId()))
                    .body(newUser);
        }
    }

    @GetMapping("/user/recent/location/{userId}")
    public ResponseEntity<UserDto> getUserWithRecentLocation(@PathVariable String userId) {
        log.debug("REST request to get user by userID:{}", userId);
        UserDto dto = userService.getUserWithRecentLocation(userId);
        return ResponseEntity.ok().body(dto);
    }

}
