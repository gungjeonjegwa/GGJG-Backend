package com.example.gungjeonjegwa.domain.order.service

import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderListRequest

interface OrderService {

    fun createOrderList(request: CreateOrderListRequest)
}