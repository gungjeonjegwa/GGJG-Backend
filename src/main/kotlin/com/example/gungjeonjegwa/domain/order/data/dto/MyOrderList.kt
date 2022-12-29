package com.example.gungjeonjegwa.domain.order.data.dto

import com.example.gungjeonjegwa.domain.order.data.enum.ActivityType
import java.time.LocalDateTime
import java.time.ZonedDateTime

class MyOrderList(
    val orderId: String,

    val activityType: ActivityType,

    val title: String,

    val price: Long?,

    val preview_url: String,

    val createdDate: LocalDateTime
) {
}