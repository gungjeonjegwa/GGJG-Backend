package com.example.gungjeonjegwa.domain.order.data.request

class CreateOrderRequest(
    val breadId: Long,

    val count: Long,

    val price: Long,

    val discountPrice: Long?,

    val unit: String?,

    val age: Long?,

    val couponId: Long? = null
) {
}