package br.com.thiagoodev.criptoio.application.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginDto(
    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Email must be a valid email address")
    val email: String,
    @field:NotBlank(message = "Password must not be blank")
    val password: String,
)
