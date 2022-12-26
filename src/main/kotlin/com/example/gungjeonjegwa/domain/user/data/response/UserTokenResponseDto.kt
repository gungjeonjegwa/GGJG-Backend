package com.example.gungjeonjegwa.domain.user.data.response

import java.time.LocalDateTime

class UserTokenResponseDto(
    val expiredAt: LocalDateTime,

    val accessToken: String,

    val refreshToken: String
) {
}