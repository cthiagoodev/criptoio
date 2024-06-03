package br.com.thiagoodev.criptoio.infrastructure.data

import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyPrice

interface CryptocurrencyPriceApiClient {
    fun list(page: Int, limit: Int): List<CryptocurrencyPrice>
}