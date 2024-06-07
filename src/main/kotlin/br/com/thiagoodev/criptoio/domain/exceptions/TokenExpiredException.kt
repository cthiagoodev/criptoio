package br.com.thiagoodev.criptoio.domain.exceptions

class TokenExpiredException(private val newMessage: String? = null) : Exception() {
    override val message: String
        get() = newMessage ?: "Your token has expired"
}