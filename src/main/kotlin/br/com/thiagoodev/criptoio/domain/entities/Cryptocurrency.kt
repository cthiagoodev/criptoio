package br.com.thiagoodev.criptoio.domain.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.NaturalId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.Base64

@Entity
@Table(name = "cryptocurrency")
class Cryptocurrency(
    @Id
    val id: String,
    @NaturalId
    @Column(unique = true)
    @field:NotBlank(message = "Symbol cannot be blank")
    val symbol: String,
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,
    @CreatedDate
    val created: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val modified: LocalDateTime = LocalDateTime.now(),
    @field:Min(value = 1, message = "Total supply must be a positive number")
    val totalSupply: BigDecimal,
    @field:Min(value = 0, message = "Current price cannot be negative")
    val currentPrice: BigDecimal,
    @field:NotBlank(message = "Logo URL cannot be blank")
    val logo: String,
    @OneToMany(mappedBy = "cryptocurrency", cascade = [CascadeType.ALL])
    val history: List<CryptoVariationPrice> = emptyList(),
) {
    companion object {
        fun generateId(key: String): String {
            val base64: ByteArray = Base64.getEncoder().encode(key.toByteArray())
            return String(base64, Charsets.UTF_8)
        }
    }
}