package com.example.gungjeonjegwa.domain.basket.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class LessRequestDataException : BasicException(ErrorCode.LESS_REQUEST_DATA) {
}