package br.com.thiagoodev.criptoio.infrastructure.services

import br.com.thiagoodev.criptoio.domain.exceptions.InvalidClaimsException
import br.com.thiagoodev.criptoio.domain.exceptions.InvalidUserTokenException
import br.com.thiagoodev.criptoio.domain.exceptions.TokenExpiredException
import br.com.thiagoodev.criptoio.domain.exceptions.TokenExtractionException
import com.nimbusds.jose.util.Base64URL
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {
    @Value("\${security.jwt.secret-key}")
    private lateinit var secretKey: String

    @Value("\${security.jwt.expiration}")
    private val expiration: Long = 0

    fun getExpiration(): Long = expiration

    private fun buildKey(): SecretKey {
        val bytes: ByteArray = Base64URL.from(secretKey).decode()
        return Keys.hmacShaKeyFor(bytes)
    }

    fun buildToken(userDetails: UserDetails): String {
        return Jwts
            .builder()
            .subject(userDetails.username)
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(buildKey())
            .compact()
    }

    fun tokenIsValid(token: String, userDetails: UserDetails): Boolean {
        val pureToken: String = extractToken(token) ?: throw TokenExtractionException()
        val claims: Claims = getClaims(pureToken) ?: throw InvalidClaimsException()

        return validateInfo(claims, userDetails)
    }

    fun getSubject(token: String): String? {
        val pureToken: String = extractToken(token) ?: throw TokenExtractionException()
        val claims: Claims = getClaims(pureToken) ?: throw InvalidClaimsException()

        return claims.subject
    }

    private fun getClaims(token: String): Claims? {
        return Jwts
            .parser()
            .verifyWith(buildKey())
            .build()
            .parseSignedClaims(token)
            ?.payload
    }

    private fun validateInfo(claims: Claims, userDetails: UserDetails): Boolean {
        if(claims.subject != userDetails.username) {
            throw InvalidUserTokenException()
        }

        if(claims.expiration.before(Date(System.currentTimeMillis()))) {
            throw TokenExpiredException()
        }

        return true
    }

    private fun extractToken(token: String): String? {
        val regex = "Bearer\\\\s([A-Za-z0-9-_]+\\\\.[A-Za-z0-9-_]+\\\\.[A-Za-z0-9-_]+)".toRegex()
        return regex.find(token)?.value
    }
}