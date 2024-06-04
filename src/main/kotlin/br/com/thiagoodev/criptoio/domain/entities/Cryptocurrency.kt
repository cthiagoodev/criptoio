package br.com.thiagoodev.criptoio.domain.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "cryptocurrency")
class Cryptocurrency(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),
    @field:NotBlank(message = "Symbol cannot be blank")
    val symbol: String,
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,
    @CreatedDate
    val createdDate: LocalDateTime = LocalDateTime.now(),
    @field:Min(value = 1, message = "Total supply must be a positive number")
    val totalSupply: BigDecimal,
    @field:Min(value = 0, message = "Current price cannot be negative")
    val currentPrice: BigDecimal,
    @field:NotBlank(message = "Logo URL cannot be blank")
    val logo: String,
)