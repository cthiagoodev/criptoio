package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptoVariationPriceRepository
import org.springframework.stereotype.Service

@Service
class CryptoVariationPriceService(private val repository: JpaCryptoVariationPriceRepository) {
    fun getAll(): List<CryptoVariationPrice> {
        return repository.findAll()
    }
}