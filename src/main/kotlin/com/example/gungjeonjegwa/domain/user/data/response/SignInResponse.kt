package com.example.gungjeonjegwa.domain.user.data.response

import java.time.LocalDateTime
import java.util.*

class SignInResponse(
    val expiredAt: LocalDateTime,

    val accessToken: String,

    val refreshToken: String
) {
}