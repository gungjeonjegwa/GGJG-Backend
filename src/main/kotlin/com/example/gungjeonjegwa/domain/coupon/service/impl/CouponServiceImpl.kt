package com.example.gungjeonjegwa.domain.coupon.service.impl

import com.example.gungjeonjegwa.domain.bread.exception.BreadNotFoundException
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.coupon.data.dto.CouponDto
import com.example.gungjeonjegwa.domain.coupon.data.entity.Coupon
import com.example.gungjeonjegwa.domain.coupon.data.entity.CouponType
import com.example.gungjeonjegwa.domain.coupon.data.entity.MyCoupon
import com.example.gungjeonjegwa.domain.coupon.data.enum.ClassType
import com.example.gungjeonjegwa.domain.coupon.data.enum.DisCountType
import com.example.gungjeonjegwa.domain.coupon.exception.AlreadyCouponException
import com.example.gungjeonjegwa.domain.coupon.exception.CouponCodeNotFoundException
import com.example.gungjeonjegwa.domain.coupon.exception.ExpiredCouponException
import com.example.gungjeonjegwa.domain.coupon.repository.CouponRepository
import com.example.gungjeonjegwa.domain.coupon.repository.CouponTypeRepository
import com.example.gungjeonjegwa.domain.coupon.repository.MyCouponRepository
import com.example.gungjeonjegwa.domain.coupon.service.CouponService
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CouponServiceImpl(
    private val couponRepository: CouponRepository,
    private val myCouponRepository: MyCouponRepository,
    private val couponTypeRepository: CouponTypeRepository,
    private val breadRepository: BreadRepository,
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

    override fun getCouponByUser(bread: String): MutableList<CouponDto> {
        if(bread == "ALL") {
            val currentUser = userUtil.fetchCurrentUser()
            var myCouponDtoList: MutableList<CouponDto> = mutableListOf()
            val myCouponList = myCouponRepository.findAllByUser(currentUser!!)
            for(myCoupon in myCouponList) {
                if(myCoupon.isUsed) continue
                if(LocalDateTime.now().isAfter(myCoupon.coupon.finishDate)) {
                    continue
                } // 쿠폰 사용한 날짜가 만료기간보다 많을때,
                val couponDto = CouponDto(
                    myCouponId = myCoupon.id,
                    name = myCoupon.coupon.name,
                    createdAt = myCoupon.createdAt,
                    finishedAt = myCoupon.coupon.finishDate,
                    disCountType = myCoupon.coupon.disCountType,
                    price = myCoupon.coupon.couponPrice
                )
                myCouponDtoList.add(couponDto)
            }
            return myCouponDtoList
        } else {
            val breadId: Long = bread.toLong()
            val breadCategory = breadRepository.findById(breadId)
                .orElseThrow { BreadNotFoundException() }
            val currentUser = userUtil.fetchCurrentUser()
            var myCouponDtoList: MutableList<CouponDto> = mutableListOf()
            var myCouponClassTypeList: MutableList<MyCoupon> = mutableListOf()
            val myCouponList = myCouponRepository.findAllByUser(currentUser!!).forEach {
                for(couponType in it.coupon.couponType) {
                    if(couponType.classType.name == breadCategory.category.name || couponType.classType.name == ClassType.ALL.name) {
                        myCouponClassTypeList.add(it)
                    }
                }
            }
            for(coupon in myCouponClassTypeList) {
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

    }

    override fun createCoupon() {
        val coupon: Coupon = Coupon(
            id = "1111222233336666",
            name = "30%퍼센트 할인쿠폰 (빵)",
            disCountType = DisCountType.PERCENT,
            couponPrice = 30,
            isEnabledCoupon = true,
            finishDate = LocalDateTime.now())
        val coupon1: Coupon = Coupon(
            id = "1111222233337777",
            name = "20%퍼센트 할인쿠폰 (케이크)",
            disCountType = DisCountType.PERCENT,
            couponPrice = 20,
            isEnabledCoupon = true,
            finishDate = LocalDateTime.now())
        val coupon2: Coupon = Coupon(
            id = "1111222233338888",
            name = "2000원 할인쿠폰 (케이크)",
            disCountType = DisCountType.NORMAL,
            couponPrice = 2000,
            isEnabledCoupon = true,
            finishDate = LocalDateTime.now())
        val couponType: CouponType = CouponType(
            id = 0,
            classType = ClassType.BREAD,
            coupon = coupon
        )
        val couponType2: CouponType = CouponType(
            id = 0,
            classType = ClassType.CAKE,
            coupon = coupon1
        )
        val couponType3: CouponType = CouponType(
            id = 0,
            classType = ClassType.CAKE,
            coupon = coupon2
        )
        couponRepository.save(coupon)
        couponRepository.save(coupon1)
        couponRepository.save(coupon2)
        couponTypeRepository.save(couponType)
        couponTypeRepository.save(couponType2)
        couponTypeRepository.save(couponType3)
        val list: MutableList<String> = mutableListOf()
    }
}