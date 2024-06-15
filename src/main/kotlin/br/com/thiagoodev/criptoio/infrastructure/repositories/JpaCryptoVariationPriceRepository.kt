package br.com.thiagoodev.criptoio.infrastructure.repositories

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.repositories.CryptoVariationPriceRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaCryptoVariationPriceRepository :
    CryptoVariationPriceRepository,
    JpaRepository<CryptoVariationPrice, UUID>,
    PagingAndSortingRepository<CryptoVariationPrice, UUID> {
        fun findByCryptocurrencyId(id: String, pageable: Pageable): Page<CryptoVariationPrice>
    }