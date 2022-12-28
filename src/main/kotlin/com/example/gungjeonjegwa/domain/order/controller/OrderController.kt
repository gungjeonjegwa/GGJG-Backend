package com.example.gungjeonjegwa.domain.order.controller

import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderListRequest
import com.example.gungjeonjegwa.domain.order.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping()
    fun createOrderList(@RequestBody request: CreateOrderListRequest): ResponseEntity<*> {
        return ResponseEntity(orderService.createOrderList(request), HttpStatus.CREATED)
    }
}