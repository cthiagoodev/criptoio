package br.com.thiagoodev.criptoio.domain.exceptions

class InvalidUserTokenException(private val newMessage: String? = null) : Exception() {
    override val message: String
        get() = newMessage ?: "The provided user token is invalid: the token is either null, empty, or failed validation."
}