package com.example.gungjeonjegwa.global.configuration.security.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class ExpiredTokenException : BasicException(ErrorCode.EXPIRED_TOKEN)