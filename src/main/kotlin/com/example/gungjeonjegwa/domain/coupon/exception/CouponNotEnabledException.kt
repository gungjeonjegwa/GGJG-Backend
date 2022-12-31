package com.example.gungjeonjegwa.domain.coupon.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class CouponNotEnabledException : BasicException(ErrorCode.COUPON_CODE_NOT_FOUND) {
}