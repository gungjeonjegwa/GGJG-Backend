package com.example.gungjeonjegwa.domain.coupon.repository

import com.example.gungjeonjegwa.domain.coupon.data.entity.CouponType
import org.springframework.data.jpa.repository.JpaRepository

interface CouponTypeRepository : JpaRepository<CouponType, Long> {
}