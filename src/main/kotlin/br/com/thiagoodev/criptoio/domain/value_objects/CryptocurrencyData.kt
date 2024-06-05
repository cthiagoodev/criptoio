package br.com.thiagoodev.criptoio.domain.value_objects

import java.math.BigDecimal

data class CryptocurrencyData(
    val uuid: String,
    val symbol: String,
    val name: String,
    val created: String,
    val modified: String,
    val totalSupply: BigDecimal,
    val currentPrice: BigDecimal,
    val logo: String,
)