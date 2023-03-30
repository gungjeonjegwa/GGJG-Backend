package com.example.gungjeonjegwa.domain.coupon.service

import com.example.gungjeonjegwa.domain.coupon.data.dto.CouponDto

interface CouponService {
    fun addCouponFromUser(code: Long)

    fun createCoupon()

    fun getCouponByUser(breadId: String): List<CouponDto>
}