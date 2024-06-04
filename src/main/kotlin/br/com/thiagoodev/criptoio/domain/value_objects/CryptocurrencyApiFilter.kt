package br.com.thiagoodev.criptoio.domain.value_objects

import java.math.BigDecimal

data class CryptocurrencyApiFilter(
    val page: Int,
    val limit: Int,
    val min: BigDecimal,
    val max: BigDecimal,
)
