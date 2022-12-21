package com.example.gungjeonjegwa.domain.bread.util.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadSizeDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
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

    override fun toDto(entity: BreadDetail): BreadDetailDto = BreadDetailDto(
        id = entity.id,
        content = entity.content,
        size = entity.size,
        stroage = entity.stroage,
        expirationDate = entity.expirationDate,
        previewUrl = entity.previewUrl,
        precaution = entity.precaution,
        deliveryNotice = entity.deliveryNotice,
        allergy = entity.allergy,
        ingredient = entity.ingredient,
    )
}