package com.college.hotlittlepigs.user.dto

import com.college.hotlittlepigs.user.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserSaveDTO {
    @NotBlank(message = "Name is Required")
    lateinit var name: String

    @Email(message = "Invalid email address")
    lateinit var email: String

    @Size(min = 8, max = 16, message = "Password size must be between 8 and 16")
    lateinit var password: String

    fun toUser(): User = User(name, email, password)
}