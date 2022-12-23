package com.example.gungjeonjegwa.global.exception.exceptions

import com.example.gungjeonjegwa.global.exception.ErrorCode

open class BasicException(val errorCode: ErrorCode): RuntimeException(errorCode.msg)