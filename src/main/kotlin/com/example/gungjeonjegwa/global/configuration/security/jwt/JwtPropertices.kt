package com.example.gungjeonjegwa.global.configuration.security.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.nio.charset.StandardCharsets
import java.security.Key

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
class JwtPropertices(
    accessSecret: String,
    refreshSecret: String
) {
    val accessSecert: Key
    val refreshSecret: Key

    init {
        this.accessSecert = Keys.hmacShaKeyFor(accessSecret.toByteArray(StandardCharsets.UTF_8))
        this.refreshSecret = Keys.hmacShaKeyFor(refreshSecret.toByteArray(StandardCharsets.UTF_8))
    }
}