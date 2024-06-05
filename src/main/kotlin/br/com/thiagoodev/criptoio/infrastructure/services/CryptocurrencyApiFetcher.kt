package br.com.thiagoodev.criptoio.infrastructure.services

import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import br.com.thiagoodev.criptoio.domain.exceptions.BadRequestException
import br.com.thiagoodev.criptoio.domain.exceptions.InternalServerException
import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyPrice
import br.com.thiagoodev.criptoio.domain.value_objects.ExtractionResult
import br.com.thiagoodev.criptoio.infrastructure.api.CryptocurrencyPriceApiClient
import org.springframework.stereotype.Service
import java.util.logging.Level
import java.util.logging.Logger

@Service
class CryptocurrencyApiFetcher(private val api: CryptocurrencyPriceApiClient) {
    private val logger: Logger = Logger.getLogger("CryptocurrencyApiFetcherLogger")

    fun extract(): ExtractionResult {
        try {
            val response: List<CryptocurrencyPrice> = api.list(1, 50, "brl")
            val cryptos: List<Cryptocurrency> = response.map { it.toCryptocurrency() }
            return ExtractionResult.Success(cryptos)
        } catch(error: BadRequestException) {
            logger.log(Level.WARNING, error.message)
            return ExtractionResult.Error(BadRequestException("Error on fetch API: ${error.message}"))
        } catch(error: InternalServerException) {
            logger.log(Level.SEVERE, error.message)
            return ExtractionResult.Error(InternalServerException("Error on server API: ${error.message}"))
        } catch(error: Exception) {
            logger.log(Level.WARNING, error.message)
            return ExtractionResult.Error(Exception("Error on extract API: ${error.message}"))
        }
    }
}