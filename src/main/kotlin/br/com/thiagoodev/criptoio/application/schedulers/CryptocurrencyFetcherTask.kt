package br.com.thiagoodev.criptoio.application.schedulers

import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.domain.value_objects.ExtractionResult
import br.com.thiagoodev.criptoio.infrastructure.repositories.JpaCryptoVariationPriceRepository
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
    private val cryptocurrencyRepository: JpaCryptocurrencyRepository,
    private val cryptoVariationPriceRepository: JpaCryptoVariationPriceRepository,
) {
    private val logger = Logger.getLogger("CryptocurrencyFetcherTaskException")

    @Scheduled(fixedRate = 300000)
    fun findDataAndSave() {
        try {
            val response: ExtractionResult = fetcher.extract()
            if(response is ExtractionResult.Success<*>) {
                val cryptos = response.result as List<Cryptocurrency>
                val variations: List<CryptoVariationPrice> = cryptos.map { it.toCryptocurrencyVariationPrice() }

                cryptocurrencyRepository.saveAll(cryptos)
                cryptoVariationPriceRepository.saveAll(variations)
            }
        } catch(error: PersistenceException) {
            logger.log(Level.SEVERE, error.message)
        }
    }
}