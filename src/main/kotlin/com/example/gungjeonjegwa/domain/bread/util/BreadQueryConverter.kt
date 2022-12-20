package com.example.gungjeonjegwa.domain.bread.util

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import java.util.stream.Stream

interface BreadQueryConverter {
    fun toQueryDto(entity: Stream<BreadDto>, last: Boolean): BreadQueryDto
}