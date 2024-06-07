package br.com.thiagoodev.criptoio.application.dtos

data class JwtDto(
    val access: String,
    val expiration: Long,
)
