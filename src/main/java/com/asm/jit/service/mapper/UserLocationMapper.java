package com.asm.jit.service.mapper;

import com.asm.jit.model.UserLocation;
import com.asm.jit.service.dto.UserLocationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserLocationMapper extends EntityMapper<UserLocationDto, UserLocation> {

    default UserLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserLocation userLocation = new UserLocation();
        userLocation.setId(id);
        return userLocation;
    }

}
