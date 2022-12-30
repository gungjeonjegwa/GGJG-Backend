package com.example.gungjeonjegwa.domain.coupon.data.entity

import com.example.gungjeonjegwa.domain.coupon.data.enum.ClassType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class CouponType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val classType: ClassType,

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    val coupon: Coupon
) {
}