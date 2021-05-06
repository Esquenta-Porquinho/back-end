package com.college.hotlittlepigs.security

import com.college.hotlittlepigs.exception.ErrorResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.JwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter : OncePerRequestFilter() {
    val jwtManager = JwtManager()

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, filter: FilterChain) {
        val jwt = req.getHeader(HttpHeaders.AUTHORIZATION).orEmpty()
        if (isJwtProviderInvalid(jwt)) return invalidJwtProvider(res)

        setAuthenticationContext(jwt, res)

        filter.doFilter(req, res)
    }

    private fun isJwtProviderInvalid(jwt: String): Boolean = jwt.startsWith(SecurityConstant.JWT_PROVIDER).not()

    private fun invalidJwtProvider(res: HttpServletResponse) = writeError(res, SecurityConstant.JWT_INVALID_MSG)

    private fun setAuthenticationContext(jwt: String, res: HttpServletResponse) {
        try {
            SecurityContextHolder.getContext().authentication = buildAuthentication(jwt)
        } catch (ex: JwtException) {
            invalidJwtDuringParse(ex, res)
        }
    }

    private fun invalidJwtDuringParse(ex: JwtException, res: HttpServletResponse) {
        writeError(res, ex.message!!)
    }

    private fun writeError(res: HttpServletResponse, error: String) {
        val response = jacksonObjectMapper().writeValueAsString(ErrorResponse(error))
        res.writer.write(response)
        res.contentType = MediaType.APPLICATION_JSON_VALUE
        res.status = HttpStatus.BAD_REQUEST.value()
    }

    private fun buildAuthentication(jwt: String): UsernamePasswordAuthenticationToken {
        val jwtWithoutProvider = jwt.removePrefix(SecurityConstant.JWT_PROVIDER)
        val claims = jwtManager.parseToken(jwtWithoutProvider)
        val email = claims.subject
        val roles = claims[SecurityConstant.JWT_ROLE_KEY] as List<*>
        val grantedAuthorities = roles.map { SimpleGrantedAuthority(it.toString()) }
        return UsernamePasswordAuthenticationToken(email, null, grantedAuthorities)
    }
}