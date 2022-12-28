package com.example.gungjeonjegwa.domain.basket.data.dto

class BasketDto(
    val id: Long,
    val title: String,
    val previewUrl: String,
    val breadId: Long,
    val age: Long?,
    val price: Long,
    val size: String?,
    val extramoney: Long?,
    val unit: String?,
    val isSoldOut: Boolean,
    val count: Int,
    val remaincount: Long,
) {
}