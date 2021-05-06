package com.college.hotlittlepigs.security

// TODO load these values from ENV/PROP variables
object SecurityConstants {
    const val JWT_EXP_DAYS = 5
    const val API_KEY = "123"
    const val JWT_PROVIDER = "IFC"
    const val JWT_ROLE_KEY = "role"
    const val JWT_INVALID_MSG = "Invalid JWT Token"
}