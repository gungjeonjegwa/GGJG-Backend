package com.example.gungjeonjegwa.domain.order.data.dto

class MyOrderDetailDto(
    val title: String,

    val age: Long?,

    val unit: String?,

    val size: String?,

    val previewUrl: String,

    val discountPrice: Long?,

    val price: Long,

    val count: Long
) {
}