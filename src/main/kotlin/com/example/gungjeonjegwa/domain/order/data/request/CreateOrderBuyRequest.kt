package com.example.gungjeonjegwa.domain.order.data.request

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto

class CreateOrderBuyRequest(
    val isPayment: Boolean = false,

    val orderId: String,

    val address: AddressDto,

    val list: List<CreateOrderRequest>
) {
}