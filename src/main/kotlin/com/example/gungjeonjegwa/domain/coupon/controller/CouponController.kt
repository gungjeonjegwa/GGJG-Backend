package com.example.gungjeonjegwa.domain.coupon.controller

import com.example.gungjeonjegwa.domain.coupon.data.dto.CouponDto
import com.example.gungjeonjegwa.domain.coupon.service.CouponService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponController(
    private val couponService: CouponService
) {
    @GetMapping("/my")
    fun getCouponByUser(@RequestParam(value = "breadId", required = false, defaultValue = "ALL") breadId: String): MutableList<CouponDto> {
        return couponService.getCouponByUser(breadId)
    }

    @PostMapping("/{code}")
    fun addCoupon(@PathVariable("code") code: Long) {
        couponService.addCouponFromUser(code)
    }

    @PostMapping
    fun createCoupon() {
        couponService.createCoupon()
    }
}