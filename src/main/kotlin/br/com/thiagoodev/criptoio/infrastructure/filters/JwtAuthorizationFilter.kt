package br.com.thiagoodev.criptoio.infrastructure.filters

import br.com.thiagoodev.criptoio.infrastructure.services.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthorizationFilter(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authorizationHeader: String? = request.getHeader("Authorization")

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return filterChain.doFilter(request, response)
        }

        val email: String? = jwtService.getSubject(authorizationHeader)
        val user: UserDetails = userDetailsService.loadUserByUsername(email)
        val tokenIsValid: Boolean = jwtService.tokenIsValid(authorizationHeader, user)

        if(tokenIsValid) {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(user.username, user.password, user.authorities))
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}