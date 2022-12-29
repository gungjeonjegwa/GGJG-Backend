package com.example.gungjeonjegwa.domain.order.exception

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException

class OrderIdNotFoundException : BasicException(ErrorCode.ORDERID_NOT_FOUND){
}