package br.com.thiagoodev.criptoio.application.controllers

import br.com.thiagoodev.criptoio.domain.exceptions.*
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
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
    fun cryptocurrencyException(
        error: Exception,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val status: HttpStatusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value())
        val detail = ProblemDetail.forStatusAndDetail(status, error.message)
        return ResponseEntity.of(detail).build()
    }

    @ExceptionHandler(
        UsernameNotFoundException::class,
        UserNotAuthenticatedException::class,
    )
    fun userException(
        error: Exception,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val status: HttpStatusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value())
        val detail = ProblemDetail.forStatusAndDetail(status, error.message ?: "User not found")
        return ResponseEntity.of(detail).build()
    }

    @ExceptionHandler(
        SignatureException::class,
        MalformedJwtException::class,
        ExpiredJwtException::class,
        UnsupportedJwtException::class,
        InvalidUserTokenException::class,
        ForbiddenException::class,
    )
    fun authorizationException(
        error: Exception,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val statusCode: HttpStatus = when(error) {
            is SignatureException -> HttpStatus.CONFLICT
            is MalformedJwtException -> HttpStatus.BAD_GATEWAY
            is ExpiredJwtException -> HttpStatus.UNAUTHORIZED
            is UnsupportedJwtException -> HttpStatus.FORBIDDEN
            is ForbiddenException -> HttpStatus.FORBIDDEN
            is InvalidUserTokenException -> HttpStatus.UNAUTHORIZED
            else -> HttpStatus.BAD_REQUEST
        }

        val status: HttpStatusCode = HttpStatusCode.valueOf(statusCode.value())
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