package com.college.hotlittlepigs.user.dto

import com.college.hotlittlepigs.user.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserUpdateDTO {
    @NotBlank
    lateinit var name: String

    @Email
    lateinit var email: String

    // TODO verify why min size = 7 here and UserSaveDTO is different
    @Size(min = 7, max = 99)
    lateinit var password: String

    fun toUser(): User = User(name, email, password)
}