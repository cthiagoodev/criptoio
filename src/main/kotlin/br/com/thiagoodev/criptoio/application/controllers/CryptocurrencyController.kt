package br.com.thiagoodev.criptoio.application.controllers

import br.com.thiagoodev.criptoio.application.services.CryptoVariationPriceService
import br.com.thiagoodev.criptoio.application.services.CryptocurrencyService
import br.com.thiagoodev.criptoio.domain.entities.CryptoVariationPrice
import br.com.thiagoodev.criptoio.domain.entities.Cryptocurrency
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/crypto")
class CryptocurrencyController(
    private val service: CryptocurrencyService,
    private val variationPriceService: CryptoVariationPriceService,
) {
    @GetMapping("/")
    fun getAll(): ResponseEntity<List<Cryptocurrency>> {
        val cryptos: List<Cryptocurrency> = service.getAll()
        return ResponseEntity.ok(cryptos)
    }

    @GetMapping("/{id}/history")
    fun history(@PathVariable("id") cryptocurrency: String): ResponseEntity<List<CryptoVariationPrice>> {
        val variation: List<CryptoVariationPrice> = variationPriceService.findByCryptocurrencyId(cryptocurrency)
        return ResponseEntity.ok(variation)
    }
}