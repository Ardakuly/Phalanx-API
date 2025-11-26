package com.backbone.phalanx.user.mapper;

import com.backbone.phalanx.user.dto.UserDto;
import com.backbone.phalanx.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}
