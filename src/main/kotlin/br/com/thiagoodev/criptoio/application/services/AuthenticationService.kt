package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.application.dtos.JwtDto
import br.com.thiagoodev.criptoio.application.dtos.LoginDto
import br.com.thiagoodev.criptoio.infrastructure.services.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
) {
    fun login(form: LoginDto): JwtDto {
        val userAuthentication = UsernamePasswordAuthenticationToken(form.email, form.password)
        val authentication: Authentication = authenticationManager.authenticate(userAuthentication)

        SecurityContextHolder.getContext().authentication = authentication

        if(!authentication.isAuthenticated) {
            throw Exception()
        }

        val user: UserDetails = userService.findByEmail(form.email)
        val token: String = jwtService.buildToken(user)
        val expiration: Long = jwtService.getExpiration()

        return JwtDto(token, expiration)
    }
}