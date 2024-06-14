package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptocurrencyRepository
import org.springframework.stereotype.Service

@Service
class CryptocurrencyService(private val repository: JpaCryptocurrencyRepository) {
    fun getAll(): List<Cryptocurrency> {
        val cryptocurrencies: MutableList<Cryptocurrency> = repository.findAll()

        for(crypto in cryptocurrencies) {
            val history: List<CryptoVariationPrice> = crypto.history
            crypto.history = history.sortedBy { it.created }.take(10).toMutableList()
        }

        return cryptocurrencies
    }

    fun saveAll(values: List<Cryptocurrency>): List<Cryptocurrency> {
        return repository.saveAll(values)
    }
}