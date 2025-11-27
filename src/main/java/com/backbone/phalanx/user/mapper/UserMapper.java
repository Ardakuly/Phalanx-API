package com.backbone.phalanx.user.mapper;

import com.backbone.phalanx.user.dto.UserDto;
import com.backbone.phalanx.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "enabled", expression = "java(entity.isEnabled())")
    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}
