package br.com.thiagoodev.criptoio.domain.value_objects

import java.math.BigDecimal

data class CryptocurrencyPrice(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: BigDecimal,
    val totalVolume: BigDecimal,
    val priceChange24h: BigDecimal,
    val priceChangePercentage24h: BigDecimal,
    val totalSupply: BigDecimal,
    val lastUpdated: String
)
