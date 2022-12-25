package com.example.gungjeonjegwa.domain.user.data.dto

class UserDto(
    val id: String,

    val password: String,

    val name: String,

    val phone: String?,

    val email: String,

    val refreshToken: String?
) {
}