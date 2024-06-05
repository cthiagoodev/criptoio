package br.com.thiagoodev.criptoio.application.schedulers

import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.domain.value_objects.ExtractionResult
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptocurrencyRepository
import br.com.thiagoodev.criptoio.infrastructure.services.CryptocurrencyApiFetcher
import jakarta.persistence.PersistenceException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger

@Component
class CryptocurrencyFetcherTask(
    private val fetcher: CryptocurrencyApiFetcher,
    private val repository: JpaCryptocurrencyRepository,
) {
    private val logger = Logger.getLogger("PersistenceCryptocurrencyException")

    @Scheduled(fixedRate = 300000)
    fun findData() {
        try {
            val response: ExtractionResult = fetcher.extract()

            if(response is ExtractionResult.Success<*>) {
                repository.saveAll(response.result as List<Cryptocurrency>)
            } else {
                val error = (response as ExtractionResult.Error).error
                logger.log(Level.WARNING, error.message)
            }
        } catch(error: PersistenceException) {
            logger.log(Level.SEVERE, error.message)
        }
    }
}