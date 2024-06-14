package br.com.thiagoodev.criptoio.application.controllers

import br.com.thiagoodev.criptoio.application.dtos.JwtDto
import br.com.thiagoodev.criptoio.application.dtos.LoginDto
import br.com.thiagoodev.criptoio.application.dtos.RefreshTokenDto
import br.com.thiagoodev.criptoio.application.services.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthenticationController(private val authenticationService: AuthenticationService) {
    @PostMapping("/login")
    fun login(@RequestBody form: LoginDto): ResponseEntity<JwtDto> {
        val jwt: JwtDto = authenticationService.login(form)
        return ResponseEntity.ok(jwt)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody form: RefreshTokenDto): ResponseEntity<JwtDto> {
        val jwt: JwtDto = authenticationService.refresh(form.token)
        return ResponseEntity.ok(jwt)
    }
}