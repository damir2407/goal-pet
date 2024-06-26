package org.example.goalpet.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.google.common.base.Strings
import jakarta.validation.ConstraintViolationException
import mu.KotlinLogging
import org.example.goalpet.dto.response.ErrorResponse
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.UnsupportedMediaType
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.OffsetDateTime


@RestControllerAdvice
class PublicRestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse {

        val fields = e.bindingResult.fieldErrors

        return ErrorResponse(
            errorMessage = "Неправильно введенны поля! ${fields.joinToString { it.field }}",
            timestamp = OffsetDateTime.now(),
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(e: ConstraintViolationException): ErrorResponse {
        val response = ErrorResponse(
            errorMessage = "Неправильно введенны поля! ${
                e.constraintViolations.joinToString {
                    (it.propertyPath as PathImpl).leafNode.name
                }
            }}",
            timestamp = OffsetDateTime.now()
        )
        return response
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ErrorResponse {
        val fields = if (e.rootCause is MissingKotlinParameterException) {
            (e.rootCause as MissingKotlinParameterException).path.joinToString(".") { it.fieldName }
        } else if (e.cause is InvalidFormatException) {
            (e.cause as InvalidFormatException).path.joinToString(".") { it.fieldName }
        } else ""

        val response = ErrorResponse(
            errorMessage = "Неправильно введенны поля! $fields",
            timestamp = OffsetDateTime.now()
        )
        return response
    }


    @ExceptionHandler(Exception::class, MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(e: Exception): ErrorResponse {
        return ErrorResponse(
            errorMessage = "Произошла ошибка! Попробуйте еще раз!",
            timestamp = OffsetDateTime.now()
        )
    }

    @ExceptionHandler(RoomException::class, VisitorException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCustomException(e: RuntimeException): ErrorResponse {
        return ErrorResponse(
            errorMessage = e.message!!,
            timestamp = OffsetDateTime.now()
        )
    }
}