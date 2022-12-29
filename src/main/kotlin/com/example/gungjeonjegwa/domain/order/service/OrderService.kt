package com.example.gungjeonjegwa.domain.order.service

import com.example.gungjeonjegwa.domain.order.data.dto.MyOrderDetailListDto
import com.example.gungjeonjegwa.domain.order.data.dto.MyOrderList
import com.example.gungjeonjegwa.domain.order.data.dto.OrderId
import com.example.gungjeonjegwa.domain.order.data.dto.OrderListDto
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderBuyRequest

interface OrderService {
    fun createOrderList(request: CreateOrderBuyRequest)

    fun createOrderId(): OrderId

    fun selectBuyOrder(): OrderListDto

    fun findMyOrderList(): MutableList<MyOrderList>

    fun findMyDetailOrder(orderId: String): MyOrderDetailListDto
}