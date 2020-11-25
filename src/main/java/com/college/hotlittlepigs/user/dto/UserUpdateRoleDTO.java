package com.college.hotlittlepigs.user.dto;

import com.college.hotlittlepigs.user.enums.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateRoleDTO {

  @NotNull(message = "Role required")
  private Role role;
}
