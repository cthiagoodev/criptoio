package br.com.thiagoodev.criptoio.domain.exceptions

class InternalServerException(private val newMessage: String? = null) : RuntimeException() {
    override val message: String?
        get() = newMessage ?: super.message
}