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
        val limitPage = 50
        var currentPage = 1
        val results: MutableList<CryptocurrencyPrice> = mutableListOf()

        try {
            while(currentPage <= limitPage) {
                val response: List<CryptocurrencyPrice> = api.list(currentPage, limitPage)
                results.addAll(response)
                currentPage++
            }

            val cryptos: List<Cryptocurrency> = results.map(::parseToCryptocurrency)
            repository.saveAll(cryptos)
        } catch(error: Exception) {

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