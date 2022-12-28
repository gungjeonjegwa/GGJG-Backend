package com.example.gungjeonjegwa.domain.order.data.dto

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto

class OrderListDto(
    val address: AddressDto?,

    val name: String,

    val phone: String?,
) {
}