package com.example.gungjeonjegwa.domain.bread.util.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadLikeDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadSearchDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import org.springframework.stereotype.Component

@Component
class BreadConverterImpl : BreadConverter {
    override fun toDto(entity: Bread): BreadDto = BreadDto(
        id = entity.id,
        title = entity.title,
        price = entity.price,
        category = entity.category,
        isSoldOut = entity.isSoldOut,
        previewUrl = entity.previewUrl,
        sellDeliveryType = entity.sellDeliveryType,
        isLikeItem = false
    )

    override fun toDto(entity: BreadDetail, id: Long, title: String): BreadDetailDto = BreadDetailDto(
        id = id,
        title = title,
        content = entity.content,
        size = entity?.size,
        stroage = entity.storage,
        expirationDate = entity.expirationDate,
        previewUrl = entity.previewUrl,
        precaution = entity.precaution,
        deliveryNotice = entity.deliveryNotice,
        allergy = entity.allergy,
        ingredient = entity.ingredient,
        deliveryPrice = entity.deliveryPrice
    )

    override fun toDto(entity: BreadDto, isLike: Boolean): BreadLikeDto = BreadLikeDto(
        id = entity.id,
        title = entity.title,
        price = entity.price,
        category = entity.category,
        isSoldOut = entity.isSoldOut,
        previewUrl = entity.previewUrl,
        sellDeliveryType = entity.sellDeliveryType,
        isLikeItem = isLike
    )

    override fun toSearchDto(entity: Bread): BreadSearchDto = BreadSearchDto(
        title = entity.title
    )
}