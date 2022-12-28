package com.example.gungjeonjegwa.domain.order.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class PaymentFaildException : BasicException(ErrorCode.PAYMENT_FAILED) {
}