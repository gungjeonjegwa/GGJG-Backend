package com.example.gungjeonjegwa.global.configuration.security.config

import com.example.gungjeonjegwa.global.configuration.security.filter.ExceptionFilter
import com.example.gungjeonjegwa.global.configuration.security.filter.JwtTokenFilter
import com.example.gungjeonjegwa.global.configuration.security.jwt.TokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfig(
    private val jwtTokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>(){
    override fun configure(builder: HttpSecurity) {
        val jwtTokenFilter = JwtTokenFilter(jwtTokenProvider)
        val exceptionFilter = ExceptionFilter(objectMapper)
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}