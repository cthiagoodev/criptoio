package br.com.thiagoodev.criptoio.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "crypto_variation_price")
class CryptoVariationPrice(
    @Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),
    @ManyToOne
    @JsonIgnoreProperties(value = ["history"])
    @JoinColumn(name = "cryptocurrency_id")
    val cryptocurrency: Cryptocurrency,
    @field:Min(value = 0, message = "You cannot enter a value less than 0")
    val price: BigDecimal,
    @CreatedDate
    val created: LocalDateTime = LocalDateTime.now(),
)