package com.example.gungjeonjegwa.domain.basket.service

import com.example.gungjeonjegwa.domain.basket.data.CountResponse
import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto
import com.example.gungjeonjegwa.domain.basket.data.request.BasketCreateRequest

interface BasketService {
    fun findBasketByUser(): List<BasketDto>

    fun deleteBasketByUser(id: Long)

    fun plusCount(id: Long): CountResponse

    fun minusCount(id: Long): CountResponse

    fun createBasket(basket: List<BasketCreateRequest>)
}
