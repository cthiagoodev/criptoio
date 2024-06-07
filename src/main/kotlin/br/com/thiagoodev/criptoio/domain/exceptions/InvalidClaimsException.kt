package br.com.thiagoodev.criptoio.domain.exceptions

class InvalidClaimsException(private val newMessage: String? = null) : Exception() {
    override val message: String
        get() = newMessage
            ?: ("Unable to extract claims from the token." +
                    "The token format is invalid or the token is empty. Please provide a valid JWT token.")
}