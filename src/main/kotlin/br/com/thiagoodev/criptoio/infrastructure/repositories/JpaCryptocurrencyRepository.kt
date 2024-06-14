package br.com.thiagoodev.criptoio.infrastructure.repositories

import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.domain.repositories.CryptocurrencyRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaCryptocurrencyRepository :
    CryptocurrencyRepository,
    JpaRepository<Cryptocurrency, String>,
    PagingAndSortingRepository<Cryptocurrency, String>