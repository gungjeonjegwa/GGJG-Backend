package com.example.gungjeonjegwa.domain.order.data.dto

import com.example.gungjeonjegwa.domain.order.data.enum.ActivityType
import java.time.LocalDateTime

class MyOrderList(
    val orderId: String,

    val activityType: ActivityType,

    val title: String,

    val price: Long?,

    val previewUrl: String,

    val createdDate: LocalDateTime
) {
}