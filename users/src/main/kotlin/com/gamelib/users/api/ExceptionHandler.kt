package com.gamelib.users.api

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.gamelib.users.core.exceptions.EmailUsedException
import com.gamelib.users.core.exceptions.GameNotOwnedException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailUsedException::class, GameNotOwnedException::class, IllegalStateException::class)
    fun invalidRequest() {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun notFound() {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: MethodArgumentNotValidException) =
        ErrorResponse.of(ex.fieldErrors.map { "${it.field} ${it.defaultMessage}" })

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(ex: ConstraintViolationException) =
        ErrorResponse.of(ex.constraintViolations.map { "${it.propertyPath} ${it.message}" })

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(ex: HttpMessageNotReadableException): ErrorResponse =
        when (val c = ex.cause) {
            is MissingKotlinParameterException -> "${c.parameter.name} is required"
            is MismatchedInputException -> "cannot parse ${c.path.last().fieldName}"
            else -> "Invalid Request"
        }.let { ErrorResponse(it) }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException::class)
    fun forbidden() {
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    fun unauthorized() {
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun catchAll(ex: Exception) {
    }
}