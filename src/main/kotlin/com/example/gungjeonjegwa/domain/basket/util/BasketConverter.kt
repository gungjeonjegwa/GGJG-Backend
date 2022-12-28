package com.example.gungjeonjegwa.domain.basket.util

import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto
import com.example.gungjeonjegwa.domain.basket.data.entity.Basket

interface BasketConverter {
    fun toDto(entity: Basket): BasketDto

    fun toDto(entity: Basket, count: Int): BasketDto
}