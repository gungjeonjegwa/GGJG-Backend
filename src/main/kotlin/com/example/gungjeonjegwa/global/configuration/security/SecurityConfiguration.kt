package com.example.gungjeonjegwa.global.configuration.security

import com.example.gungjeonjegwa.global.configuration.security.config.FilterConfig
import com.example.gungjeonjegwa.global.configuration.security.filter.ExceptionFilter
import com.example.gungjeonjegwa.global.configuration.security.filter.JwtTokenFilter
import com.example.gungjeonjegwa.global.configuration.security.jwt.TokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtTokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .antMatchers("/users/signup").permitAll()
            .antMatchers("/users/signin").permitAll()
            .antMatchers("/users/refresh").permitAll()
            .antMatchers("/users/idcheck").permitAll()
            .antMatchers("/users/emailcheck").permitAll()
            .antMatchers(HttpMethod.GET, "/bread/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))
            .and()
            .apply(FilterConfig(jwtTokenProvider, objectMapper))
            .and()
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(12)
}