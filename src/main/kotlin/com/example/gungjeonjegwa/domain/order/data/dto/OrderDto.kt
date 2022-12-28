package com.example.gungjeonjegwa.domain.order.data.dto

class OrderDto(
    val breadId: Long,

    val title: String,

    val age: Long?,

    val unit: String?,

    val extramoney: Long?,

    val price: Long,

    val count: Long,
) {
}