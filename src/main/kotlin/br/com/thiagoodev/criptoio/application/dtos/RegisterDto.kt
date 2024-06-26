package br.com.thiagoodev.criptoio.application.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class RegisterDto(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Email must be a valid email address")
    val email: String,
    @field:NotBlank(message = "Password must not be blank")
    val password: String,
    @field:NotBlank
    val cpf: String,
    @field:NotNull(message = "Date of birth must not be blank")
    val dateOfBirth: LocalDate,
) {
    fun validate(): Boolean {
        return name.isNotEmpty() &&
                email.isNotEmpty() &&
                password.isNotEmpty() &&
                cpf.isNotEmpty()
    }

    fun validateCpf(): Boolean {
        val regex = Regex("^[0-9]{11}$")
        return regex.matches(cpf)
    }
}
