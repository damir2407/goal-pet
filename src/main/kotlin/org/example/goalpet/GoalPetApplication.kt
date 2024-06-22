package org.example.goalpet

import org.example.goalpet.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class GoalPetApplication

fun main(args: Array<String>) {
    runApplication<GoalPetApplication>(*args)
}
