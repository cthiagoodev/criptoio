package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.application.dtos.RegisterDto
import br.com.thiagoodev.criptoio.domain.entities.User
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaUserRepository
import br.com.thiagoodev.criptoio.infrastructure.services.JwtService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDetailsService: UserDetailsService,
    private val jpaUserRepository: JpaUserRepository,
    private val encoder: BCryptPasswordEncoder,
    private val jwtService: JwtService,
) {
    fun findByEmail(email: String): User {
        return userDetailsService.loadUserByUsername(email) as User
    }

    fun create(form: RegisterDto): User {
        val user = User(
            name = form.name,
            email = form.email,
            password = encoder.encode(form.password),
            cpf = form.cpf,
            dateOfBirth = form.dateOfBirth,
        )
        return jpaUserRepository.save(user)
    }
}