package com.example.gungjeonjegwa.domain.user.service

import com.example.gungjeonjegwa.domain.user.data.request.SignInRequest
import com.example.gungjeonjegwa.domain.user.data.request.SignUpRequest
import com.example.gungjeonjegwa.domain.user.data.response.SignInResponse
import com.example.gungjeonjegwa.domain.user.data.response.SignUpResponse
import com.example.gungjeonjegwa.domain.user.data.response.UserTokenResponseDto

interface UserService {
    fun signUp(request: SignUpRequest): SignUpResponse

    fun signIn(request: SignInRequest): SignInResponse

    fun refreshTokenExecute(refreshToken: String): UserTokenResponseDto

    fun signOut()
}