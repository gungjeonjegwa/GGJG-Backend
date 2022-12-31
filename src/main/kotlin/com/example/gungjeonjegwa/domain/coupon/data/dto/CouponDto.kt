package com.example.gungjeonjegwa.domain.coupon.data.dto

import com.example.gungjeonjegwa.domain.coupon.data.enum.DisCountType
import java.time.LocalDate
import java.time.LocalDateTime

class CouponDto(
    val myCouponId: Long,

    val name: String,

    val createdAt: LocalDateTime,

    val finishedAt: LocalDateTime,

    val disCountType: DisCountType,

    val price: Long,
) {
}