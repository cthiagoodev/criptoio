package br.com.thiagoodev.criptoio.infrastructure.filters

import br.com.thiagoodev.criptoio.infrastructure.services.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAuthorizationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val authorizationHeader: String? = request.getHeader("Authorization")

            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
                return filterChain.doFilter(request, response)
            }

            val isNotAuthenticated: Boolean = SecurityContextHolder.getContext().authentication == null

            if(isNotAuthenticated) {
                val email: String? = jwtService.getSubject(authorizationHeader)
                val user: UserDetails = userDetailsService.loadUserByUsername(email)
                val tokenIsValid: Boolean = jwtService.tokenIsValid(authorizationHeader, user)

                if(tokenIsValid) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        user.username,
                        user.password,
                        user.authorities,
                    ).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }

                    SecurityContextHolder.getContext().authentication = authentication
                }
            }

            filterChain.doFilter(request, response)
        } catch (error: Exception) {
            handlerExceptionResolver.resolveException(request, response, null, error)
        }
    }
}