package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.exceptions.CryptocurrencyNotExistsException
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptoVariationPriceRepository
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptocurrencyRepository
import org.springframework.stereotype.Service

@Service
class CryptoVariationPriceService(
    private val repository: JpaCryptoVariationPriceRepository,
    private val cryptocurrencyRepository: JpaCryptocurrencyRepository,
) {
    fun getAll(): List<CryptoVariationPrice> {
        return repository.findAll()
    }

    fun findByCryptocurrencyId(id: String): List<CryptoVariationPrice> {
        if(!cryptocurrencyRepository.existsById(id)) {
            throw CryptocurrencyNotExistsException("Cryptocurrency $id not exists")
        }

        return repository.findByCryptocurrencyId(id)
    }
}