package com.example.gungjeonjegwa.domain.user.util

import com.example.gungjeonjegwa.domain.user.data.dto.UserDto
import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.domain.user.data.request.SignUpRequest

interface UserConverter {
    fun toDto(request: SignUpRequest): UserDto
    fun toEntity(dto: UserDto, password: String): User
}