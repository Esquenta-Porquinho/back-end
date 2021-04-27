package com.college.hotlittlepigs.user.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserLoginDTO(
    @Email(message = "Invalid email address") val email: String,
    @NotBlank(message = "Password required") val password: String
)