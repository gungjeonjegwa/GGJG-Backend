package com.example.gungjeonjegwa.domain.coupon.controller

import com.example.gungjeonjegwa.domain.coupon.service.CouponService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponController(
    private val couponService: CouponService
) {
    @PostMapping("/{code}")
    fun addCoupon(@PathVariable("code") code: Long) {
        couponService.addCouponFromUser(code)
    }
