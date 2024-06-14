package br.com.thiagoodev.criptoio.application.dtos

import jakarta.validation.constraints.NotEmpty

data class RefreshTokenDto(
    @field:NotEmpty(message = "Token cannot be empty")
    val token: String,
)
