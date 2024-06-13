package br.com.thiagoodev.criptoio.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),

    @field:NotBlank(message = "You need to provide the name.")
    val name: String,

    @field:NotBlank(message = "You need to provide an email address.")
    @field:Email(message = "Please provide a valid email address.")
    val email: String,

    @JsonIgnore
    @field:NotEmpty(message = "You need to provide a password.")
    private val password: String,

    @field:NotEmpty(message = "You need to provide a cpf.")
    val cpf: String,

    @field:NotNull(message = "You need to provide your date of birth.")
    val dateOfBirth: LocalDate,

    @CreatedDate
    val created: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val modified: LocalDateTime = LocalDateTime.now(),
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}