package com.example.gungjeonjegwa.domain.bread.data.request

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadImageDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadSizeDto
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.delivery.data.dto.SellDeliveryTypeDto

class AdminCreateBreadRequest(
    val title: String,

    val price: Long,

    val category: Category,

    val deliveryPrice: Long,

    val count: Long,

    val preview_url: String,

    val content: String,

    val size: Long,

    val storage: String,

    val expirationDate: String,

    val precaution: String,

    val deliveryNotice: String,

    val allergy: String,

    val ingredient: String,

    val breadImage: MutableList<BreadImageDto>,

    val breadSize: MutableList<BreadSizeDto>,

    val sellDeliveryType: MutableList<SellDeliveryTypeDto>

) {
}