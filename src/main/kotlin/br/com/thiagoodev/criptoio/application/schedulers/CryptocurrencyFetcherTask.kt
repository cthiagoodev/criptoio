package br.com.thiagoodev.criptoio.application.schedulers

import br.com.thiagoodev.criptoio.application.services.CryptoVariationPriceService
import br.com.thiagoodev.criptoio.application.services.CryptocurrencyService
import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.value_objects.ExtractionResult
import br.com.thiagoodev.criptoio.infrastructure.services.CryptocurrencyApiFetcher
import jakarta.persistence.PersistenceException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger

@Component
class CryptocurrencyFetcherTask(
    private val fetcher: CryptocurrencyApiFetcher,
    private val cryptocurrencyService: CryptocurrencyService,
    private val cryptoVariationPriceService: CryptoVariationPriceService,
) {
    private val logger = Logger.getLogger("CryptocurrencyFetcherTaskException")

    @Scheduled(fixedRate = 86400000)
    fun findDataAndSave() {
        try {
            val response: ExtractionResult = fetcher.extract()

            if(response is ExtractionResult.Success) {
                val cryptos = response.result
                val variations: List<CryptoVariationPrice> = cryptos.map { it.toCryptocurrencyVariationPrice() }

                cryptocurrencyService.saveAll(cryptos)
                cryptoVariationPriceService.saveAll(variations)
            }
        } catch(error: PersistenceException) {
            logger.log(Level.SEVERE, error.message)
        }
    }
}