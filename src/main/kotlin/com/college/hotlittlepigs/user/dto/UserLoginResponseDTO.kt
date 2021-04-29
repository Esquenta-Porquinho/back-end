package com.college.hotlittlepigs.user.dto

data class UserLoginResponseDTO(
    val token: String,
    val expireIn: Long,
    val tokenProvider: String,
)