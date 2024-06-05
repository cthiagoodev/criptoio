package br.com.thiagoodev.criptoio.infrastructure.api

import br.com.thiagoodev.criptoio.domain.exceptions.BadRequestException
import br.com.thiagoodev.criptoio.domain.exceptions.InternalServerException
import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyApiFilter
import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyPrice
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.util.logging.Level
import java.util.logging.Logger

@Service
class CryptocurrencyPriceApiClientImp : CryptocurrencyPriceApiClient {
    @Value("\${api.base.url}")
    private lateinit var baseUrl: String

    @Value("\${api.key}")
    private lateinit var apiKey: String

    private val logger: Logger = Logger.getLogger("CryptocurrencyPriceApiClientLogger")

    override fun list(page: Int, limit: Int, base: String): List<CryptocurrencyPrice> {
        val url = "${baseUrl}/api/v3/coins/markets?vs_currency=$base&page=$page&per_page=$limit"

        try {
            val client = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultHeader("x-cg-pro-api-key", apiKey)
                .build()

            val result: String? = client
                .get()
                .retrieve()
                .bodyToMono(String::class.java)
                .block()

            if(result == null) {
                return emptyList()
            }

            val mapper = ObjectMapper()
            val json = mapper.readTree(result)
            val data = json.asIterable()

            return data.map {
                 CryptocurrencyPrice(
                    id = it["id"].asText(),
                    symbol = it["symbol"].asText(),
                    name = it["name"].asText(),
                    image = it["image"].asText(),
                    priceChange24h = it["price_change_24h"].asDouble().toBigDecimal(),
                    currentPrice = it["current_price"].asDouble().toBigDecimal(),
                    priceChangePercentage24h = it["price_change_percentage_24h"].asDouble().toBigDecimal(),
                    lastUpdated = it["last_updated"].asText(),
                    totalSupply = it["total_supply"].asDouble().toBigDecimal(),
                    totalVolume = it["total_volume"].asDouble().toBigDecimal(),
                )
            }
        } catch(error: WebClientResponseException) {
            logger.log(Level.WARNING, error.message)

            throw when(error.statusCode) {
                HttpStatus.BAD_REQUEST -> BadRequestException(error.message)
                else -> InternalServerException(error.message)
            }
        } catch(error: Exception) {
            logger.log(Level.WARNING, error.message)
            throw error
        }
    }

    override fun list(filter: CryptocurrencyApiFilter): List<CryptocurrencyPrice> {
        TODO("Not yet implemented")
    }
}