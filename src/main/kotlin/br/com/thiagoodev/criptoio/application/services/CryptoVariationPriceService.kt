package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.exceptions.CryptocurrencyNotExistsException
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptoVariationPriceRepository
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptocurrencyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CryptoVariationPriceService(
    private val repository: JpaCryptoVariationPriceRepository,
    private val cryptocurrencyRepository: JpaCryptocurrencyRepository,
) {
    fun findByCryptocurrencyId(id: String, page: Int, size: Int): Page<CryptoVariationPrice> {
        if(!cryptocurrencyRepository.existsById(id)) {
            throw CryptocurrencyNotExistsException("Cryptocurrency $id not exists")
        }

        val pageable = PageRequest.of(page, size)
        return repository.findByCryptocurrencyId(id, pageable)
    }

    fun saveAll(values: List<CryptoVariationPrice>): List<CryptoVariationPrice> {
        return repository.saveAll(values)
    }
}