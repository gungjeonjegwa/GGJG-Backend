package com.example.gungjeonjegwa.domain.user.service.impl

import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.domain.user.data.request.SignInRequest
import com.example.gungjeonjegwa.domain.user.data.request.SignUpRequest
import com.example.gungjeonjegwa.domain.user.data.response.DuplicatedResponse
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
    override fun signUp(request: SignUpRequest) {
        val isUser: Boolean = userRepository.existsById(request.id)
        if(isUser) {
            throw RuntimeException("유저가 존재합니다")
        }
        return userConverter.toDto(request)!!
            .let { userConverter.toEntity(it, passwordEncoder.encode(it.password)) }
            .let { userRepository.save(it) }
    }

    @Transactional
    override fun signIn(request: SignInRequest): SignInResponse {
        val userEntity = userRepository.findById(request.id)
        if(userEntity.isEmpty) {
            throw RuntimeException("유저가 없습니다.")
        }
        val user: User = userEntity.get()
        if(!passwordEncoder.matches(request.password, user.password)) {
            throw RuntimeException("패스워드가 맞지 않습니다.")
        }
        val accessToken = tokenProvider.generatedAccessToken(user.id)
        val refreshToken = tokenProvider.generatedRefreshToken(user.id)
        user.updateRefreshToken(refreshToken)
        val loginResponse: SignInResponse = SignInResponse(accessToken, refreshToken)
        return loginResponse
    }

    @Transactional
    override fun refreshTokenExecute(refreshToken: String): UserTokenResponseDto {
        if(tokenProvider.isRefreshTokenExpired(refreshToken)) {
            throw ExpiredTokenException()
        }
        val userId = tokenProvider.exactIdFromRefreshToken(refreshToken)
        val user = userRepository.findById(userId).orElseThrow{ RuntimeException("유저 정보를 찾을수 없습니다.") }
        if(user.refreshtoken == null || user.refreshtoken != refreshToken) {
            throw InvalidTokenException()
        }
        val access = tokenProvider.generatedAccessToken(userId)
        val refresh = tokenProvider.generatedRefreshToken(userId)
        user.updateRefreshToken(refresh)
        return UserTokenResponseDto(access, refresh)
    }

    @Transactional
    override fun signOut() {
        val currentUser = userUtil.fetchCurrentUser()
        if (currentUser != null) {
            currentUser.updateRefreshToken(null)
        }
    }

    override fun checkId(id: String): DuplicatedResponse {
        val existsUserById: Boolean = userRepository.existsById(id)
        return DuplicatedResponse(existsUserById)
    }
    }
}