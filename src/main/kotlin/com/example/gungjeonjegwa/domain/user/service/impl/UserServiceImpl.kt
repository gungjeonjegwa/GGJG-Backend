package com.example.gungjeonjegwa.domain.user.service.impl

import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.domain.user.data.request.SignInRequest
import com.example.gungjeonjegwa.domain.user.data.request.SignUpRequest
import com.example.gungjeonjegwa.domain.user.data.response.SignInResponse
import com.example.gungjeonjegwa.domain.user.data.response.UserTokenResponseDto
import com.example.gungjeonjegwa.domain.user.repository.UserRepository
import com.example.gungjeonjegwa.domain.user.service.UserService
import com.example.gungjeonjegwa.domain.user.util.UserConverter
import com.example.gungjeonjegwa.global.configuration.security.exception.ExpiredTokenException
import com.example.gungjeonjegwa.global.configuration.security.exception.InvalidTokenException
import com.example.gungjeonjegwa.global.configuration.security.jwt.TokenProvider
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val userConverter: UserConverter,
    val passwordEncoder: PasswordEncoder,
    val tokenProvider: TokenProvider,
    val userUtil: UserUtil
) : UserService {
    @Transactional
    override fun signUp(request: SignUpRequest): String {
        val isUser: Boolean = userRepository.existsById(request.id)
        if(isUser) {
            throw RuntimeException("유저가 존재합니다")
        }
        return userConverter.toDto(request)!!
            .let { userConverter.toEntity(it, passwordEncoder.encode(it.password)) }
            .let { userRepository.save(it).id }
    }
