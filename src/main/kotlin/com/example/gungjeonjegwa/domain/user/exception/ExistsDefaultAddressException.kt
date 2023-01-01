package com.example.gungjeonjegwa.domain.user.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class ExistsDefaultAddressException : BasicException(ErrorCode.EXISTS_DEFAULT_ADDRESS) {
}