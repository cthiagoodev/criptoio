package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.application.dtos.RegisterDto
import br.com.thiagoodev.criptoio.application.dtos.RegisterSuccessDto
import br.com.thiagoodev.criptoio.domain.entities.User
import br.com.thiagoodev.criptoio.domain.exceptions.DataConflictException
import br.com.thiagoodev.criptoio.domain.exceptions.InvalidUserTokenException
import br.com.thiagoodev.criptoio.domain.exceptions.ValidationException
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaUserRepository
import br.com.thiagoodev.criptoio.infrastructure.services.JwtService
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.core.userdetails.UsernameNotFoundException
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
        try {
            return jpaUserRepository.findByEmail(email)
        } catch(error: EmptyResultDataAccessException) {
            throw UsernameNotFoundException("User with email $email not found")
        }
    }

    fun me(token: String): User {
        val email: String = jwtService.getSubject(token) ?: throw InvalidUserTokenException()
        return findByEmail(email)
    }

    fun create(form: RegisterDto): RegisterSuccessDto {
        if(!form.validate()) {
            throw ValidationException("The information you have provided is invalid")
        }

        if(!form.validateCpf()) {
            throw ValidationException("The provided CPF is invalid.")
        }

        val user = User(
            name = form.name,
            email = form.email,
            password = encoder.encode(form.password),
            cpf = form.cpf,
            dateOfBirth = form.dateOfBirth,
        )

        try {
            val newUser: User =  jpaUserRepository.save(user)
            return RegisterSuccessDto(
                message = "Your account has been created successfully",
                uuid = newUser.uuid.toString(),
            )
        } catch(error: TransactionSystemException) {
            throw ValidationException(error.message)
        } catch(error: DataIntegrityViolationException) {
            if(error.fillInStackTrace().cause is ConstraintViolationException) {
                throw DataConflictException("It looks like an account with this email address already exists")
            }

            val message: String? = error.cause?.cause?.message

            throw DataConflictException(message)
        } catch(error: Exception) {
            throw error
        }
    }
}