package com.college.hotlittlepigs.security

import com.college.hotlittlepigs.user.dto.UserLoginResponseDTO
import java.util.Calendar
import io.jsonwebtoken.Jwts
import kotlin.Throws
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JwtManager {
    fun createToken(email: String, roles: List<String>): UserLoginResponseDTO {
        val expiration = Calendar.getInstance()
        expiration.add(Calendar.DAY_OF_MONTH, SecurityConstants.JWT_EXP_DAYS)

        val jwt = Jwts.builder()
            .setSubject(email)
            .setExpiration(expiration.time)
            .claim(SecurityConstants.JWT_ROLE_KEY, roles)
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.API_KEY.toByteArray())
            .compact()

        val expireIn = expiration.timeInMillis
        return UserLoginResponseDTO(jwt, expireIn, SecurityConstants.JWT_PROVIDER)
    }

    @Throws(JwtException::class)
    fun parseToken(jwt: String): Claims = Jwts.parser()
        .setSigningKey(SecurityConstants.API_KEY.toByteArray())
        .parseClaimsJws(jwt)
        .body
}