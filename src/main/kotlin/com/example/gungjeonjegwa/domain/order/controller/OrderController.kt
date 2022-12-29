package com.example.gungjeonjegwa.domain.order.controller

import com.example.gungjeonjegwa.domain.order.data.dto.OrderId
import com.example.gungjeonjegwa.domain.order.data.dto.OrderListDto
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderBuyRequest
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderListRequest
import com.example.gungjeonjegwa.domain.order.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping("/buy")
    fun createOrderList(@RequestBody request: CreateOrderBuyRequest): ResponseEntity<*> {
        return ResponseEntity(orderService.createOrderList(request), HttpStatus.CREATED)
    }

    @PostMapping("/issued")
    fun generatedOrderId(): OrderId {
        return orderService.createOrderId()
    }

    @GetMapping("/list")
    fun selectOrder(): OrderListDto{
        return orderService.selectOrder()

    }
}