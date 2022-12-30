package com.example.gungjeonjegwa.domain.coupon.repository

import com.example.gungjeonjegwa.domain.coupon.data.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, String> {
}