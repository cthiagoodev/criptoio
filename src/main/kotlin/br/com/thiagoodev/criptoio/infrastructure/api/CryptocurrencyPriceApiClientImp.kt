package br.com.thiagoodev.criptoio.infrastructure.api

import br.com.thiagoodev.criptoio.domain.exceptions.BadRequestException
import br.com.thiagoodev.criptoio.domain.exceptions.InternalServerException
import br.com.thiagoodev.criptoio.domain.value_objects.CryptocurrencyPrice
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

@Service
class CryptocurrencyPriceApiClientImp : CryptocurrencyPriceApiClient {
    @Value("\${api.base.url}")
    private lateinit var baseUrl: String

    override fun list(page: Int, limit: Int, base: String): List<CryptocurrencyPrice> {
        val url = "${baseUrl}/api/v3/coins/markets?vs_currency=$base&page=$page&per_page=$limit"

        try {
            val client = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()

            val result: String? = client
                .get()
                .retrieve()
                .bodyToMono(String::class.java)
                .block()

            result?.let {
                val mapper = ObjectMapper()
                val json: JsonNode = mapper.readTree(it)
                val data: Iterable<JsonNode> = json.asIterable()

                return data.map { node ->
                     CryptocurrencyPrice(
                        id = node["id"].asText(),
                        symbol = node["symbol"].asText(),
                        name = node["name"].asText(),
                        image = node["image"].asText(),
                        priceChange24h = node["price_change_24h"].asDouble().toBigDecimal(),
                        currentPrice = node["current_price"].asDouble().toBigDecimal(),
                        priceChangePercentage24h = node["price_change_percentage_24h"].asDouble().toBigDecimal(),
                        lastUpdated = node["last_updated"].asText(),
                        totalSupply = node["total_supply"].asDouble().toBigDecimal(),
                        totalVolume = node["total_volume"].asDouble().toBigDecimal(),
                    )
                }
            }

            return emptyList()

        } catch(error: WebClientResponseException) {
            throw when(error.statusCode) {
                HttpStatus.BAD_REQUEST -> BadRequestException(error.message)
                else -> InternalServerException(error.message)
            }
        } catch(error: Exception) {
            throw error
        }
    }
}