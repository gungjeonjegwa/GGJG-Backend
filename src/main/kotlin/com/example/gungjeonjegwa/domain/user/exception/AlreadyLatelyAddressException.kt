package com.example.gungjeonjegwa.domain.user.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class AlreadyLatelyAddressException : BasicException(ErrorCode.ALREADY_LATELY_ADDRESS) {
}