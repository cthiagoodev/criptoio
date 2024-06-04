package br.com.thiagoodev.criptoio.infrastructure.api

import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyApiFilter
import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyPrice

interface CryptocurrencyPriceApiClient {
    fun list(page: Int, limit: Int): List<CryptocurrencyPrice>
    fun list(filter: CryptocurrencyApiFilter): List<CryptocurrencyPrice>
}