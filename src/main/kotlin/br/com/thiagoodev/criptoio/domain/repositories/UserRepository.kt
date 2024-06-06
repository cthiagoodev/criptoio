package br.com.thiagoodev.criptoio.domain.repositories

import br.com.thiagoodev.criptoio.domain.entities.User

interface UserRepository {
    fun findByEmail(email: String?): User
}