package br.com.thiagoodev.criptoio.application.controllers

import br.com.thiagoodev.criptoio.domain.exceptions.CryptocurrencyNotExistsException
import br.com.thiagoodev.criptoio.domain.exceptions.DataConflictException
import br.com.thiagoodev.criptoio.domain.exceptions.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandlerController : ResponseEntityExceptionHandler() {
    @ExceptionHandler(
        ValidationException::class,
        DataConflictException::class,
    )
    fun validationException(
        error: Exception,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val status: HttpStatusCode = HttpStatusCode.valueOf(HttpStatus.CONFLICT.value())
        val detail = ProblemDetail.forStatusAndDetail(status, error.message)
        return ResponseEntity.of(detail).build()
    }

    @ExceptionHandler(CryptocurrencyNotExistsException::class)
    fun cryptocurrencyNotFoundException(
        error: CryptocurrencyNotExistsException,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val status: HttpStatusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value())
        val detail = ProblemDetail.forStatusAndDetail(status, error.message)
        return ResponseEntity.of(detail).build()
    }

    @ExceptionHandler(RuntimeException::class)
    fun genericRuntimeException(error: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val status: HttpStatusCode = HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value())
        val detail = ProblemDetail.forStatusAndDetail(status, error.message)
        return ResponseEntity.of(detail).build()
    }

    @ExceptionHandler(Exception::class)
    fun genericException(error: Exception, request: WebRequest): ResponseEntity<Any>? {
        val status: HttpStatusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())
        val detail = ProblemDetail.forStatusAndDetail(status, error.message)
        return ResponseEntity.of(detail).build()
    }
}