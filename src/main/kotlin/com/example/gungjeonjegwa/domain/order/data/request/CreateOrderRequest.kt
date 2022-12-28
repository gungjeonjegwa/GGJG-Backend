package com.example.gungjeonjegwa.domain.order.data.request

class CreateOrderRequest(
    val isPayment: Boolean = false,

    val breadId: Long,

    val count: Long,

    val price: Long,

    val unit: String? = null,

    val age: Long? = null,
) {
}