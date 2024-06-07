package br.com.thiagoodev.criptoio.domain.exceptions

class CryptocurrencyNotExistsException(private val newMessage: String? = null) : Exception() {
    override val message: String
        get() = newMessage ?: "Cryptocurrency not found"
}