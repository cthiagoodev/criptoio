package br.com.thiagoodev.criptoio.infrastructure.services

import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImp(private val repository: JpaUserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return repository.findByEmail(username)
    }
}