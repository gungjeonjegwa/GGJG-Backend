package com.example.gungjeonjegwa.domain.order.data.request

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto

class CreateOrderBuyRequest(
    val isPayment: Boolean = false,

    val address: AddressDto,

    val list: List<CreateOrderRequest>
) {
}