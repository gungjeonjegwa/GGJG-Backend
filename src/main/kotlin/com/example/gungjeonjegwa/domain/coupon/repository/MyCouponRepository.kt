package com.example.gungjeonjegwa.domain.coupon.repository

import com.example.gungjeonjegwa.domain.coupon.data.entity.Coupon
import com.example.gungjeonjegwa.domain.coupon.data.entity.MyCoupon
import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface MyCouponRepository : JpaRepository<MyCoupon, Long> {
    fun existsByCouponAndUser(coupon: Coupon, user: User): Boolean
}