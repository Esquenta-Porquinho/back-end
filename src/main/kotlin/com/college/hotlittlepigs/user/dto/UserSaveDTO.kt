package com.college.hotlittlepigs.user.dto

import com.college.hotlittlepigs.user.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

class UserSaveDTO {
    @NotEmpty
    lateinit var name: String

    @Email
    lateinit var email: String

    @Size(min = 8, max = 16)
    lateinit var password: String

    fun toUser(): User = User(name, email, password)
}