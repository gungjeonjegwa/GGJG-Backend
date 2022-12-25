package com.example.gungjeonjegwa.domain.bread.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class BreadDetailNotFoundException : BasicException(ErrorCode.BREADDETAIL_NOT_FOUND) {
}