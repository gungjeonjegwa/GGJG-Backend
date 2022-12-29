package com.example.gungjeonjegwa.domain.order.data.dto

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto

class MyOrderDetailListDto(
    val address: AddressDto?,

    val orderId: String,

    val name: String,

    val phone: String?,

    val list: List<MyOrderDetailDto>
) {
}