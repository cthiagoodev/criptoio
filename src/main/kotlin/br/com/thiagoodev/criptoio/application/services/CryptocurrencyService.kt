package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptocurrencyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CryptocurrencyService(private val repository: JpaCryptocurrencyRepository) {
    fun getAll(page: Int, size: Int): Page<Cryptocurrency> {
        val pageable = PageRequest.of(page, size)
        return repository.findAll(pageable)
                .also { it.forEach(::sortAndTakeShowCaseCryptoHistory) }

    }

    private fun sortAndTakeShowCaseCryptoHistory(crypto: Cryptocurrency) {
        val history: List<CryptoVariationPrice> = crypto.history
        crypto.history = history.sortedBy { it.created }.take(10).toMutableList()
    }

    fun saveAll(values: List<Cryptocurrency>): List<Cryptocurrency> {
        return repository.saveAll(values)
    }
}