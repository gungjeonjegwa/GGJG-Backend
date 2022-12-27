package com.example.gungjeonjegwa.domain.basket.service

import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto

interface BasketService {
    fun findBasketByUser(): List<BasketDto>

    fun deleteBasketByUser(id: Long)

    fun plusCount(id: Long): Int

    fun minusCount(id: Long): Int
}
