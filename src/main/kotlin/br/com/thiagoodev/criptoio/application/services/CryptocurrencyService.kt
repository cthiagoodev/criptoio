package br.com.thiagoodev.criptoio.application.services

import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptocurrencyRepository
import org.springframework.stereotype.Service

@Service
class CryptocurrencyService(private val repository: JpaCryptocurrencyRepository) {
    fun getAll(): List<Cryptocurrency> {
        return repository.findAll()
    }
}