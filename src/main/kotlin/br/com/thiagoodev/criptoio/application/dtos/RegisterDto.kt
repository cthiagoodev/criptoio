package br.com.thiagoodev.criptoio.application.dtos

import java.time.LocalDateTime

data class RegisterDto(
    val name: String,
    val email: String,
    val password: String,
    val cpf: String,
    val dateOfBirth: LocalDateTime,
)
