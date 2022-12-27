package com.example.gungjeonjegwa.domain.basket.util

import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto
import com.example.gungjeonjegwa.domain.basket.data.entity.Basket
import org.springframework.stereotype.Component

@Component
class BasketConverterImpl : BasketConverter {
    override fun toDto(entity: Basket): BasketDto = BasketDto(
        id = entity.id,
        title = entity.bread.title,
        previewUrl = entity.bread.previewUrl,
        age = entity.age,
        price = entity.bread.price!!,
        size = entity.breadSize?.size,
        extramoney = entity.breadSize?.extramoney,
        unit = entity.breadSize?.unit,
        isSoldOut = entity.bread.isSoldOut,
        count = entity.count,
        remaincount = entity.bread.count
    )
}