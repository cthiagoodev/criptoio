package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.application.dtos.RegisterDto
import br.com.thiagoodev.criptoio.domain.entities.User
import br.com.thiagoodev.criptoio.domain.exceptions.ValidationException
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaUserRepository
import br.com.thiagoodev.criptoio.infrastructure.services.JwtService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionSystemException

@Service
class UserService(
    private val jpaUserRepository: JpaUserRepository,
    private val encoder: BCryptPasswordEncoder,
    private val jwtService: JwtService,
) {
    fun findByEmail(email: String): User {
        return jpaUserRepository.findByEmail(email)
    }

    fun create(form: RegisterDto): User {
        val user = User(
            name = form.name,
            email = form.email,
            password = encoder.encode(form.password),
            cpf = form.cpf,
            dateOfBirth = form.dateOfBirth,
        )

        try {
            return jpaUserRepository.save(user)
        } catch(error: TransactionSystemException) {
            throw ValidationException(error.message)
        } catch(error: Exception) {
            throw error
        }
    }
}