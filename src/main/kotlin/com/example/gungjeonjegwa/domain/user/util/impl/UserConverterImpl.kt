package com.example.gungjeonjegwa.domain.user.util.impl

import com.example.gungjeonjegwa.domain.user.data.dto.UserDto
import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.domain.user.data.request.SignUpRequest
import com.example.gungjeonjegwa.domain.user.util.UserConverter
import org.springframework.stereotype.Component

@Component
class UserConverterImpl : UserConverter {
    override fun toDto(request: SignUpRequest): UserDto = UserDto(
        id = request.id,
        password = request.password,
        name = request.name,
        phone = request.phone,
        email = request.email,
        refreshToken = null
    )
