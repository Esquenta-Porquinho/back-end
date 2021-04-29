package com.college.hotlittlepigs.user.dto


data class UserLoginServiceDTO(
    val email: String,
    val roles: List<String>
)