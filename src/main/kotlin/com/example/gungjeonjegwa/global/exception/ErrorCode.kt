package com.example.gungjeonjegwa.global.exception

enum class ErrorCode(
    val msg: String,
    val code: Int
) {
    EXPIRED_TOKEN("토큰 만료", 401),
    INVALID_TOKEN("토큰 변질", 401)
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", 404),
}