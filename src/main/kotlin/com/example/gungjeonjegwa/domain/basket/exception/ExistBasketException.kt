package com.example.gungjeonjegwa.domain.basket.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class ExistBasketException : BasicException(ErrorCode.EXIST_BASKET) {
}