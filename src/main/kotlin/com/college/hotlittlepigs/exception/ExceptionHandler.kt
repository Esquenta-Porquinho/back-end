package com.college.hotlittlepigs.exception

import com.college.hotlittlepigs.exception.response.JwtException
import com.college.hotlittlepigs.exception.response.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest,
    ): ResponseEntity<Any> = buildResponse(ex)

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException, request: WebRequest): ResponseEntity<Any> =
        buildResponse(ex)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ex.message!!))

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ex.message!!))

    private fun buildResponse(ex: Exception): ResponseEntity<Any> {
        val errors = buildErrors(ex)
        val response = LabeledErrorResponse("Ops! We got some errors during data validation", errors)
        return badRequest().body(response)
    }

    private fun buildErrors(ex: Exception): List<LabeledError> {
        return when (ex) {
            is MethodArgumentNotValidException -> ex.bindingResult.fieldErrors.map {
                LabeledError(it.field, it.defaultMessage)
            }
            is ConstraintViolationException -> ex.constraintViolations.map {
                LabeledError(it.propertyPath.toString(), it.message)
            }
            else -> listOf()
        }
    }

}



