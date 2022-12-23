package com.example.gungjeonjegwa.global.configuration.security.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class InvalidTokenException : BasicException(ErrorCode.INVALID_TOKEN)