package com.example.gungjeonjegwa.global.configuration.security.jwt

import com.example.gungjeonjegwa.global.configuration.security.auth.AuthDetailsService
import com.example.gungjeonjegwa.global.configuration.security.exception.ExpiredTokenException
import com.example.gungjeonjegwa.global.configuration.security.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenProvider(
    private val jwtPropertices: JwtPropertices,
    private val authDetailsService: AuthDetailsService
) {
    companion object {
        const val ACCESS_TYPE = "access"
        const val REFRESH_TYPE = "refresh"
        const val ACCESS_EXP = 60L * 15 // 15min
        const val REFRESH_EXP = 60L * 60 * 24 * 7 // 1 weeks
        const val TOKEN_PREFIX = "Bearer "
    }

    fun generatedAccessToken(id: String): String =
        generatedToken(id, ACCESS_TYPE, jwtPropertices.accessSecert, ACCESS_EXP)

    fun generatedRefreshToken(id: String): String =
        generatedToken(id, REFRESH_TYPE, jwtPropertices.refreshSecret, REFRESH_EXP)

    private fun generatedToken(sub: String, type: String, secret: Key, exp: Long): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub)
            .claim("type", type)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()

    fun resolveToken(req: HttpServletRequest): String? {
        val token = req.getHeader("Authorization") ?: return null
        return parseToken(token)
    }

    fun parseToken(token: String): String? =
        if (token.startsWith(TOKEN_PREFIX)) token.replace(TOKEN_PREFIX, "") else null

    fun authentication(token: String): Authentication {
        val userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token, jwtPropertices.accessSecert))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getTokenBody(token: String, secret: Key): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }

    private fun getTokenSubject(token: String, secret: Key): String =
        getTokenBody(token, secret).subject

    fun exactIdFromRefreshToken(refresh: String): String =
        getTokenSubject(refresh, jwtPropertices.refreshSecret)

    fun isRefreshTokenExpired(token: String): Boolean {
        try {
            getTokenBody(token, jwtPropertices.refreshSecret).expiration
            return false
        } catch (e: ExpiredJwtException) {
            return true
        }
    }
    fun getAccessTokenExpired(): ZonedDateTime {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusSeconds(ACCESS_EXP)
    }
}