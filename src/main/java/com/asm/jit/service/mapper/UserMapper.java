package com.asm.jit.service.mapper;

import com.asm.jit.model.User;
import com.asm.jit.service.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDto, User> {

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
    
}
