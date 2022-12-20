package com.example.gungjeonjegwa.domain.bread.util.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import org.springframework.stereotype.Component

@Component
class BreadConverterImpl : BreadConverter {
    override fun toDto(entity: Bread): BreadDto = BreadDto(
        id = entity.id,
        title = entity.title,
        price = entity.price,
        category = entity.category,
        count = entity.count,
        isSoldOut = entity.isSoldOut,
        previewUrl = entity.previewUrl,
        sellDeliveryType = entity.sellDeliveryType,
    )
}