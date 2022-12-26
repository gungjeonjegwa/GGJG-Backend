package com.example.gungjeonjegwa.domain.user.data.response

import java.time.LocalDateTime
import java.time.ZonedDateTime

class UserTokenResponseDto(
    val expiredAt: ZonedDateTime,

    val accessToken: String,

    val refreshToken: String
) {
}