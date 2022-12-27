package com.example.gungjeonjegwa.global.configuration.elasticsearch

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "elasticsearch")
class ElasticSearchProperties(
    private val host: String,
    private val port: String,
){
    fun getHostAndPort(): String {
        return "$host:$port"
    }
}