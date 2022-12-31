package com.example.gungjeonjegwa.domain.coupon.service.impl

import com.example.gungjeonjegwa.domain.coupon.data.dto.CouponDto
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
                coupon = coupon,
                user = currentUser
            )
            myCouponRepository.save(myCoupon)
        } else {
            throw ExpiredCouponException()
        }
    }

    override fun getCouponByUser(): MutableList<CouponDto> {
        val currentUser = userUtil.fetchCurrentUser()
        var myCouponDtoList: MutableList<CouponDto> = mutableListOf()
        val myCouponList = myCouponRepository.findAllByUser(currentUser!!)
        for(coupon in myCouponList) {
            if(coupon.isUsed) continue
            if(LocalDateTime.now().isAfter(coupon.coupon.finishDate)) {
                continue
            } // 쿠폰 사용한 날짜가 만료기간보다 많을때,
            val couponDto = CouponDto(
                myCouponId = coupon.id,
                name = coupon.coupon.name,
                createdAt = coupon.createdAt,
                finishedAt = coupon.coupon.finishDate,
                disCountType = coupon.coupon.disCountType,
                price = coupon.coupon.couponPrice
            )
            myCouponDtoList.add(couponDto)
        }
        return myCouponDtoList
    }

    override fun createCoupon() {
        couponRepository.save(Coupon("1111222233334444", "3천원", DisCountType.NORMAL, 3000, true, LocalDateTime.now()))
        val list: MutableList<String> = mutableListOf()
    }
}