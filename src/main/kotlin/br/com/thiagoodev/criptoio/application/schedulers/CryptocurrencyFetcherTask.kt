package br.com.thiagoodev.criptoio.application.schedulers

import br.com.thiagoodev.criptoio.infrastructure.services.CryptocurrencyApiFetcher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CryptocurrencyFetcherTask(private val fetcher: CryptocurrencyApiFetcher) {
    @Scheduled(fixedRate = 300000)
    fun findData() {
        fetcher.extract()
    }
}