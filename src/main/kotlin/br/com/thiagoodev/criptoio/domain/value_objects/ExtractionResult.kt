package br.com.thiagoodev.criptoio.domain.value_objects

import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency

sealed class ExtractionResult {
    data class Success(val result: List<Cryptocurrency>) : ExtractionResult()
    data class Error(val error: Throwable) : ExtractionResult()
}