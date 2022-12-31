package com.example.gungjeonjegwa.domain.coupon.data.entity

import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.global.entity.BaseTimeEntity
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class MyCoupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var isUsed: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    val coupon: Coupon,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) : BaseTimeEntity() {
}