package com.college.hotlittlepigs.user.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class UserLoginDTO {
    @Email
    lateinit var email: String

    @NotBlank
    lateinit var password: String
}