package com.example.gungjeonjegwa.domain.order.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class DefaultAddressNotFoundException : BasicException(ErrorCode.DEFAULT_ADDRESS_NOT_FOUND) {
}