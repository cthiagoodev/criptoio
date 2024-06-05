package br.com.thiagoodev.criptoio.domain.value_objects

sealed class ExtractionResult {
    data class Success<T>(val result: T) : ExtractionResult()
    data class Error(val error: Throwable) : ExtractionResult()
}