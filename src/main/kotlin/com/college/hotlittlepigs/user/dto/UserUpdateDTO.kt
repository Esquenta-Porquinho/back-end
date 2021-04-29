package com.college.hotlittlepigs.user.dto

import com.college.hotlittlepigs.user.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserUpdateDTO(
    @NotBlank(message = "Name is required") val name: String,
    @Email(message = "Invalid email address") val email: String,
    @Size(min = 7, max = 99, message = "Password size must be between 7 and 99") val password: String
) {
    fun toUser(): User = User(name, email, password)
}