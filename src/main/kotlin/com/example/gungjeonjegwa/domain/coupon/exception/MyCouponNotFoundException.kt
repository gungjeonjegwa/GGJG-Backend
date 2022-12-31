package com.example.gungjeonjegwa.domain.coupon.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class MyCouponNotFoundException : BasicException(ErrorCode.MYCOUPON_NOT_FOUND) {
}