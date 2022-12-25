package com.example.gungjeonjegwa.domain.bread.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class BreadNotFoundException : BasicException(ErrorCode.BREAD_NOT_FOUND) {
}