package com.backbone.phalanx.user.dto;

import com.backbone.phalanx.authorization.model.Role;

public record UserDto(
      String email,
      String firstName,
      String lastName,
      String profilePictureUrl,
      Role role
) {
}
