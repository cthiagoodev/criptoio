package br.com.thiagoodev.criptoio.domain.entities

import jakarta.persistence.*
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
    @JoinColumn(name = "cryptocurrency_id")
    val cryptocurrency: Cryptocurrency,
    val price: BigDecimal,
    @CreatedDate
    val created: LocalDateTime = LocalDateTime.now(),
)