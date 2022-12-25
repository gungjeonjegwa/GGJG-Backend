package com.example.gungjeonjegwa.global.configuration.security.filter

import com.example.gungjeonjegwa.global.configuration.security.jwt.TokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
    private val jwtTokenProvider: TokenProvider
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = jwtTokenProvider.resolveToken(request)
        if(!token.isNullOrBlank()) {
            val authentication = jwtTokenProvider.authentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}