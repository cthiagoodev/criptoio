package br.com.thiagoodev.criptoio.infrastructure.repositories

import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.domain.repositories.CryptocurrencyRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaCryptocurrencyRepository : CryptocurrencyRepository, JpaRepository<Cryptocurrency, UUID>