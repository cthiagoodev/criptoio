package br.com.thiagoodev.criptoio.infrastructure.repositories

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.repositories.CryptoVariationPriceRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaCryptoVariationPriceRepository : CryptoVariationPriceRepository, JpaRepository<CryptoVariationPrice, UUID>