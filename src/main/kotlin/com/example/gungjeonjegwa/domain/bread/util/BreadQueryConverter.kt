package com.example.gungjeonjegwa.domain.bread.util

import com.example.gungjeonjegwa.domain.bread.data.dto.*
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadImage
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import java.util.stream.Stream

interface BreadQueryConverter {
    fun toQueryDto(entity: Stream<BreadDto>, last: Boolean): BreadQueryDto

    fun toBreadSizeDto(entity: MutableList<BreadSize>): MutableList<BreadSizeDto>

    fun toBreadImageDto(entity: MutableList<BreadImage>): MutableList<BreadImageDto>

    fun toQueryDto(breadSizeDto: MutableList<BreadSizeDto>, breadDetailDto: BreadDetailDto, breadImageDto: MutableList<BreadImageDto>): BreadDetailQueryDto
}