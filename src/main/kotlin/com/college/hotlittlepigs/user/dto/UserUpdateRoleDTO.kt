package com.college.hotlittlepigs.user.dto

import com.college.hotlittlepigs.user.enums.Role
import javax.validation.constraints.NotNull

data class UserUpdateRoleDTO(@NotNull(message = "Role required") val role: Role)