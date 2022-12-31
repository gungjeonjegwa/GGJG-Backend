package com.example.gungjeonjegwa.global.exception

enum class ErrorCode(
    val msg: String,
    val code: Int
) {
    LESS_REQUEST_DATA("요구하는 Request 정보가 더 필요합니다.", 400),
    OVER_COUNT("장바구니에서 요구한 빵 개수 카운트 수가 초과하였습니다.", 400),
    PAYMENT_FAILED("미결제 사용자가 구매 요청을 보냈습니다.", 400),
    MAXIMUM_LATELY("최근 배송지 갯수를 초과하였습니다.", 400),
    UNAUTHORIZED("권한 없음", 401),
    EXPIRED_TOKEN("토큰 만료", 401),
    INVALID_TOKEN("토큰 변질", 401),
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", 404),
    PASSWORD_NOT_MATCHED("패스워드가 올바르지 않습니다.", 404),
    BREAD_NOT_FOUND("빵 정보를 찾을 수 없습니다.", 404),
    BREADDETAIL_NOT_FOUND("빵 세부정보를 찾을 수 없습니다.", 404),
    ADDRESS_NOT_FOUND("주소를 찾을 수 없습니다.", 404),
    DEFAULT_ADDRESS_NOT_FOUND("기본 배송지를 찾을 수 없습니다.", 404),
    BASKET_NOT_FOUND("장바구니에 해당하는 아이템이 존재하지 않습니다.", 404),
    ORDERID_NOT_FOUND("주문 번호를 찾을 수 없습니다.", 404),
    EXIST_NOT_BASKET("장바구니에 해당하는 아이템이 존재하지 않습니다.", 404),

    COUPON_CODE_NOT_FOUND("쿠폰 코드가 존재하지 않습니다.", 404),
    COUPON_NOT_ENABLED("쿠폰이 활성화 되어있지 않습니다.", 404),
    MYCOUPON_NOT_FOUND("내가 가지고 있는 쿠폰이 존재하지 않습니다.", 404),
    ALREADY_USED_COUPON("이미 사용되었거나, 등록되어 있는 쿠폰입니다.", 404),
    EXPIRED_COUPON("이벤트 기간이 아니거나 만료된 쿠폰입니다.", 404),
    EXIST_BASKET("장바구니에 이미 해당하는 아이템이 존재합니다.", 409),
    ALREADY_LATELY_ADDRESS("이미 존재하는 최근 배송지입니다.", 409),
    INTERNAL_SERVER_ERROR("서버 내부 에러", 500)
}