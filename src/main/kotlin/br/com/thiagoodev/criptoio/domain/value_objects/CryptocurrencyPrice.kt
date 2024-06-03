package br.com.thiagoodev.criptoio.domain.value_objects

import java.math.BigDecimal

data class CryptocurrencyPrice(
    val name: String,
    val price: BigDecimal,
)