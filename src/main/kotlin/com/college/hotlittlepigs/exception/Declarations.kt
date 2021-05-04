package com.college.hotlittlepigs.exception

open class ErrorResponse(val message: String)

open class ErrorResponseList<T>(val message: String, val errors: List<T>)

class LabeledError(val label: String, val message: String?)

class LabeledErrorResponse(message: String, errors: List<LabeledError>) :
    ErrorResponseList<LabeledError>(message, errors)
