package com.college.hotlittlepigs.user.dto;

import javax.validation.constraints.NotNull;

import com.college.hotlittlepigs.user.enums.Role;

import lombok.Data;

@Data
public class UserUpdateRoleDTO {

    @NotNull(message="Role required")
    private Role role;
}