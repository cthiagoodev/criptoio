package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.application.dtos.JwtDto
import br.com.thiagoodev.criptoio.application.dtos.LoginDto
import br.com.thiagoodev.criptoio.domain.entities.User
import br.com.thiagoodev.criptoio.domain.exceptions.UserNotAuthenticatedException
import br.com.thiagoodev.criptoio.infrastructure.services.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
) {
    fun login(form: LoginDto): JwtDto {
        try {
            val userAuthentication = UsernamePasswordAuthenticationToken(form.email, form.password)
            val authentication: Authentication = authenticationManager.authenticate(userAuthentication)

            SecurityContextHolder.getContext().authentication = authentication

            if(!authentication.isAuthenticated) {
                throw UserNotAuthenticatedException(
                    "Authentication Failed. Your request could not be authenticated. Please check the following and try again:")
            }

            val user: UserDetails = userService.findByEmail(form.email)
            val token: String = jwtService.buildToken(user)
            val expiration: Long = jwtService.getExpiration()

            return JwtDto(token, expiration)
        } catch(error: AuthenticationException) {
            throw UserNotAuthenticatedException(
                "Authentication Failed. Your request could not be authenticated. Please check the following and try again:")
        }
    }

    fun refresh(token: String): JwtDto {
        val email: String = jwtService.getSubject(token) ?: throw UsernameNotFoundException("User not found")
        val user: User = userService.findByEmail(email)
        val expiration: Long = jwtService.getExpiration()

        if(jwtService.tokenIsValid(token, user)) {
            return JwtDto(token, expiration)
        }

        val newToken: String = jwtService.buildToken(user)

        return JwtDto(newToken, expiration)
    }
}