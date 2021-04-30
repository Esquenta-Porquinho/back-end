package com.college.hotlittlepigs.security

import com.college.hotlittlepigs.user.util.toSecureHash
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomPasswordEncoder : PasswordEncoder {
    override fun encode(rawPassword: CharSequence): String = rawPassword.toString().toSecureHash()

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean =
        rawPassword.toString().toSecureHash() == encodedPassword
}