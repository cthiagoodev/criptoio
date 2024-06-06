package br.com.thiagoodev.criptoio.domain.repositories

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice

interface CryptoVariationPriceRepository {
    fun findByCryptocurrencyId(id: String): List<CryptoVariationPrice>
}