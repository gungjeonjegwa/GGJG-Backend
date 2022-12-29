package com.example.gungjeonjegwa.global.exception

enum class ErrorCode(
    val msg: String,
    val code: Int
) {
    LESS_REQUEST_DATA("요구하는 Request 정보가 더 필요합니다.", 400),
    OVER_COUNT("장바구니에서 요구한 빵 개수 카운트 수가 초과하였습니다.", 400),
    PAYMENT_FAILED("미결제 사용자가 구매 요청을 보냈습니다.", 400),
    UNAUTHORIZED("권한 없음", 401),
    EXPIRED_TOKEN("토큰 만료", 401),
    INVALID_TOKEN("토큰 변질", 401),
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", 404),
    PASSWORD_NOT_MATCHED("패스워드가 올바르지 않습니다.", 404),
    BREAD_NOT_FOUND("빵 정보를 찾을 수 없습니다.", 404),
    BREADDETAIL_NOT_FOUND("빵 세부정보를 찾을 수 없습니다.", 404),
    ADDRESS_NOT_FOUND("주소를 찾을 수 없습니다.", 404),
    BASKET_NOT_FOUND("장바구니에 해당하는 아이템이 존재하지 않습니다.", 404),
    EXIST_BASKET("장바구니에 해당하는 아이템이 이미 존재합니다.", 409),
    INTERNAL_SERVER_ERROR("서버 내부 에러", 500)
}