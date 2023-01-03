package com.example.gungjeonjegwa.domain.bread.util.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.*
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadImage
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import com.example.gungjeonjegwa.domain.bread.util.BreadQueryConverter
import org.springframework.stereotype.Component

@Component
class BreadQueryConverterImpl : BreadQueryConverter {
    override fun toQueryDto(entity: MutableList<BreadLikeDto>, last: Boolean): BreadQueryDto = BreadQueryDto(
        list = entity,
        last = last
    )

    override fun toQueryDto(entity: MutableList<BreadLikeDto>): MutableList<BreadLikeDto> {
        var list: MutableList<BreadLikeDto> = arrayListOf()
        for(e in entity) {
            list.add(BreadLikeDto(
                id = e.id,
                title = e.title,
                price = e.price,
                category = e.category,
                isSoldOut = e.isSoldOut,
                previewUrl = e.previewUrl,
                sellDeliveryType = e.sellDeliveryType,
                isLikeItem = e.isLikeItem
            ))
        }
        return list
    }

    override fun toBreadSizeDto(entity: MutableList<BreadSize>): MutableList<BreadSizeDto> {
        var list: MutableList<BreadSizeDto> = arrayListOf()
        for(e in entity) {
            list.add(BreadSizeDto(e.size, e.extramoney, e.unit))
        }
        return list
    }

    override fun toQueryDto(
        breadSizeDto: MutableList<BreadSizeDto>,
        breadDetailDto: BreadDetailDto,
        breadImageDto: MutableList<String>,
        breadImageInfoDto: MutableList<String>,
        isLikeItem: Boolean,
        bread: Bread
    ): BreadDetailQueryDto = BreadDetailQueryDto(
        id = breadDetailDto.id,
        title = breadDetailDto.title,
        content = breadDetailDto.content,
        price = bread.price,
        deliveryPrice = breadDetailDto.deliveryPrice,
        count = bread.count,
        isSoldOut = bread.isSoldOut,
        size = breadDetailDto?.size,
        storage = breadDetailDto.stroage,
        expirationDate = breadDetailDto.expirationDate,
        previewUrl = breadDetailDto.previewUrl,
        precaution = breadDetailDto.precaution,
        deliveryNotice = breadDetailDto.deliveryNotice,
        allergy = breadDetailDto.allergy,
        ingredient = breadDetailDto.ingredient,
        isLikeItem = isLikeItem,
        breadSize = breadSizeDto,
        breadImage = breadImageDto,
        breadImageInfo = breadImageInfoDto,
        sellDeliveryType = bread.sellDeliveryType
    )

    override fun toBreadImageDto(entity: MutableList<BreadImage>): MutableList<BreadImageDto> {
        var list: MutableList<BreadImageDto> = arrayListOf()
        for(e in entity) {
            list.add(BreadImageDto(e.imageUrl, e.isImageInfo))
        }
        return list
    }
}