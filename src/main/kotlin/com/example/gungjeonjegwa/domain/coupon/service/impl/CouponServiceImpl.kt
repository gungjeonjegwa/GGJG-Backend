package com.example.gungjeonjegwa.domain.coupon.service.impl

import com.example.gungjeonjegwa.domain.coupon.data.entity.Coupon
import com.example.gungjeonjegwa.domain.coupon.data.entity.MyCoupon
import com.example.gungjeonjegwa.domain.coupon.data.enum.DisCountType
import com.example.gungjeonjegwa.domain.coupon.exception.AlreadyCouponException
import com.example.gungjeonjegwa.domain.coupon.exception.CouponCodeNotFoundException
import com.example.gungjeonjegwa.domain.coupon.exception.ExpiredCouponException
import com.example.gungjeonjegwa.domain.coupon.repository.CouponRepository
import com.example.gungjeonjegwa.domain.coupon.repository.MyCouponRepository
import com.example.gungjeonjegwa.domain.coupon.service.CouponService
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.reflect.typeOf

@Service
class CouponServiceImpl(
    private val couponRepository: CouponRepository,
    private val myCouponRepository: MyCouponRepository,
    private val userUtil: UserUtil
) : CouponService {
    override fun addCouponFromUser(code: Long) {
        val currentUser = userUtil.fetchCurrentUser()
        val coupon = couponRepository.findById(code.toString()).orElseThrow{ CouponCodeNotFoundException() }
        val existsMyCoupon = myCouponRepository.existsByCouponAndUser(coupon, currentUser!!)
        if(existsMyCoupon) {
            throw AlreadyCouponException()
        }
        val before = LocalDateTime.now().isBefore(coupon.finishDate)
        if(coupon.isEnabledCoupon && before) { // 쿠폰 마감기한보다 낮으면 등록 가능
            val myCoupon = MyCoupon(
                id = 0,
                expiredAt = coupon.finishDate,
                coupon = coupon,
                user = currentUser
            )
            myCouponRepository.save(myCoupon)
        } else {
            throw ExpiredCouponException()
        }
    }
