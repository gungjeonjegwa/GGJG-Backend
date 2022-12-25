package com.example.gungjeonjegwa.domain.user.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class PasswordNotMatchedException : BasicException(ErrorCode.PASSWORD_NOT_MATCHED) {
}