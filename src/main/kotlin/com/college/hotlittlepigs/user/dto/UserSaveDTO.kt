package com.college.hotlittlepigs.user.dto

import com.college.hotlittlepigs.user.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserSaveDTO(
    @NotBlank(message = "Name is Required") val name: String,
    @Email(message = "Invalid email address") val email: String,
    @Size(min = 8, max = 16, message = "Password size must be between 8 and 16") val password: String,
) {
    fun toUser(): User = User(name, email, password)
}