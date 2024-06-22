package org.example.goalpet.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
class ApplicationProperties(
    val async: Async
) {

    data class Async(
        val corePoolSize: Int,
        val maxPoolSize: Int,
        val queueCapacity: Int
    )
}