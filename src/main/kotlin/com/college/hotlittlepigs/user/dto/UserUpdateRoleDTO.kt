package com.college.hotlittlepigs.user.dto

import com.college.hotlittlepigs.user.enums.Role
import javax.validation.constraints.NotBlank

class UserUpdateRoleDTO {
    @NotBlank
    lateinit var role: Role
}
