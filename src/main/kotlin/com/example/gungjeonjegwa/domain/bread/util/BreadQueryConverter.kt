package com.example.gungjeonjegwa.domain.bread.util

import com.example.gungjeonjegwa.domain.bread.data.dto.*
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import java.util.stream.Stream

interface BreadQueryConverter {
    fun toQueryDto(entity: Stream<BreadDto>, last: Boolean): BreadQueryDto

    fun toQueryDto(entity: MutableList<BreadSize>): MutableList<BreadSizeDto>
}