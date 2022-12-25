package com.example.gungjeonjegwa.global.exception

enum class ErrorCode(
    val msg: String,
    val code: Int
) {
    UNAUTHORIZED("권한 없음", 401),
    EXPIRED_TOKEN("토큰 만료", 401),
    INVALID_TOKEN("토큰 변질", 401),
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", 404),
    PASSWORD_NOT_MATCHED("패스워드가 올바르지 않습니다.", 404),
    BREAD_NOT_FOUND("빵 정보를 찾을 수 없습니다.", 404),
    BREADDETAIL_NOT_FOUND("빵 세부정보를 찾을 수 없습니다.", 404),
    INTERNAL_SERVER_ERROR("서버 내부 에러", 500)
}