package com.example.gungjeonjegwa.domain.bread.data.dto

import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.delivery.data.entity.SellDeliveryType

class BreadDto(
    val id: Long,

    val title: String,

    val price: Long?,

    val category: Category,

    val isSoldOut: Boolean,

    val previewUrl: String,

    val sellDeliveryType: MutableList<SellDeliveryType>,

    val isLikeItem: Boolean
) {

}