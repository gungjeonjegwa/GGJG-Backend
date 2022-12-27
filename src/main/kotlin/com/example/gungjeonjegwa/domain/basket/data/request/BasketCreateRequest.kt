package com.example.gungjeonjegwa.domain.basket.data.request

class BasketCreateRequest(
    val breadId: Long,

    val age: Long?,

    val count: Int,

    val size: String? = null,

    val extramoney: Long? = null,

    val unit: String? = null,
) {
}