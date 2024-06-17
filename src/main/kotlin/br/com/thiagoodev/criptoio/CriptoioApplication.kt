package br.com.thiagoodev.criptoio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
class CriptoioApplication

fun main(args: Array<String>) {
	runApplication<CriptoioApplication>(*args)
}
