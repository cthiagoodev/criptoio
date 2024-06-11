package br.com.thiagoodev.criptoio.domain.exceptions

class ValidationException(private val newMessage: String? = null) : Exception() {
    override val message: String?
        get() = newMessage ?: super.message
}