package br.com.thiagoodev.criptoio.infrastructure.repositories

import br.com.thiagoodev.criptoio.domain.entities.User
import br.com.thiagoodev.criptoio.domain.repositories.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaUserRepository : UserRepository, JpaRepository<User, UUID>