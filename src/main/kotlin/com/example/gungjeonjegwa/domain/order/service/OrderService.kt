package com.example.gungjeonjegwa.domain.order.service

import com.example.gungjeonjegwa.domain.order.data.dto.OrderListDto
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderBuyRequest

interface OrderService {
    fun createOrderList(request: CreateOrderBuyRequest)

}