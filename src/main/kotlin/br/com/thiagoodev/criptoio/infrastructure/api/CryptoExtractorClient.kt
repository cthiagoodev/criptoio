package br.com.thiagoodev.criptoio.infrastructure.api

import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyPrice

interface CryptoExtractorClient {
    fun extract(): List<CryptocurrencyPrice>
}