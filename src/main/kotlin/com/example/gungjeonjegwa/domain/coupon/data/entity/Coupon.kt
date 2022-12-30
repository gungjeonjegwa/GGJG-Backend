package com.example.gungjeonjegwa.domain.coupon.data.entity

import com.example.gungjeonjegwa.domain.coupon.data.enum.DisCountType
import com.example.gungjeonjegwa.global.entity.BaseTimeEntity
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class Coupon(
    @Id
    val id: String,

    val name: String,

    @Enumerated(EnumType.STRING)
    val disCountType: DisCountType,

    val couponPrice: Long,

    val isEnabledCoupon: Boolean,

    val finishDate: LocalDateTime,

    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    val couponType: MutableList<CouponType> = mutableListOf()
) : BaseTimeEntity() {
}