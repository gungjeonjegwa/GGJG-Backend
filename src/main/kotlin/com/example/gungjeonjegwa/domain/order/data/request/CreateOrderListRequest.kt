package com.example.gungjeonjegwa.domain.order.data.request

class CreateOrderListRequest(
    val isPayment: Boolean = false,

    val list: List<CreateOrderRequest>
) {
}