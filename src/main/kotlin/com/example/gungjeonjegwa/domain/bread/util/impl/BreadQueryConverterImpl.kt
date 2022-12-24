package com.example.gungjeonjegwa.domain.bread.util.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.*
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
        breadImageDto: MutableList<BreadImageDto>,
        isLikeItem: Boolean
    ): BreadDetailQueryDto = BreadDetailQueryDto(
        id = breadDetailDto.id,
        content = breadDetailDto.content,
        size = breadDetailDto.size,
        stroage = breadDetailDto.stroage,
        expirationDate = breadDetailDto.expirationDate,
        previewUrl = breadDetailDto.previewUrl,
        precaution = breadDetailDto.precaution,
        deliveryNotice = breadDetailDto.deliveryNotice,
        allergy = breadDetailDto.allergy,
        ingredient = breadDetailDto.ingredient,
        isLikeItem = isLikeItem,
        breadSize = breadSizeDto,
        breadImage = breadImageDto
    )

    override fun toBreadImageDto(entity: MutableList<BreadImage>): MutableList<BreadImageDto> {
        var list: MutableList<BreadImageDto> = arrayListOf()
        for(e in entity) {
            list.add(BreadImageDto(e.imageUrl, e.isImageInfo))
        }
        return list
    }
}