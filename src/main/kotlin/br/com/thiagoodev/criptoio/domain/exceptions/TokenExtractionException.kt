package br.com.thiagoodev.criptoio.domain.exceptions

class TokenExtractionException(private val newMessage: String? = null) : Exception() {
    override val message: String
        get() = newMessage ?: "Unable to extract token from string. Invalid or empty token format"
}