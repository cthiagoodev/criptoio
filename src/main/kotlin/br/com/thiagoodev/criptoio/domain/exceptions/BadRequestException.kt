package br.com.thiagoodev.criptoio.domain.exceptions

class BadRequestException(private val newMessage: String? = null) : Exception() {
    override val message: String?
        get() = newMessage ?: super.message
}