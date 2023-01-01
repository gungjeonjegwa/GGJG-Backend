package com.example.gungjeonjegwa.domain.user.data.response

class MyProfileResponse(
    val name: String,

    val couponCount: Long,

    val stampCount: Long,

    val waitOrderCount: Long,

    val inDeliverCount: Long,

    val completeDelivery: Long,

    val cancelCount: Long,

    val returnCount: Long
) {
}