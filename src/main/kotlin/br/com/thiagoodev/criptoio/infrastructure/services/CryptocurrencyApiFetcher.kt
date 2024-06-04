package br.com.thiagoodev.criptoio.infrastructure.services

import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyPrice
import br.com.thiagoodev.criptoio.infrastructure.api.CryptocurrencyPriceApiClient
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptocurrencyRepository
import org.springframework.stereotype.Service

@Service
class CryptocurrencyApiFetcher(
    private val repository: JpaCryptocurrencyRepository,
    private val api: CryptocurrencyPriceApiClient,
) {
    fun extract() {
        try {
            val response: List<CryptocurrencyPrice> = api.list(1, 50, "brl")
            val cryptos: List<Cryptocurrency> = response.map(::parseToCryptocurrency)
            repository.saveAll(cryptos)
        } catch(error: Exception) {
            println(error)
        }
    }

    private fun parseToCryptocurrency(value: CryptocurrencyPrice): Cryptocurrency {
        return Cryptocurrency(
            symbol = value.symbol,
            name = value.name,
            logo = value.image,
            currentPrice = value.currentPrice,
            totalSupply = value.totalSupply,
        )
    }
}