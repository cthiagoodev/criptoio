package br.com.thiagoodev.criptoio.application.controllers

import br.com.thiagoodev.criptoio.application.dtos.RegisterDto
import br.com.thiagoodev.criptoio.application.services.UserService
import br.com.thiagoodev.criptoio.domain.entities.User
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody form: RegisterDto): ResponseEntity<User> {
        val user: User = userService.create(form)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }
}