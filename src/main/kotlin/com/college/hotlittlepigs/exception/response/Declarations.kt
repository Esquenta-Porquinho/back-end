package com.college.hotlittlepigs.exception.response

class JwtException(msg: String) : RuntimeException(msg)

open class NotFoundException(msg: String) : RuntimeException(msg)