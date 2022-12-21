package com.example.gungjeonjegwa.domain.bread.util.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.util.BreadQueryConverter
import org.springframework.stereotype.Component
import java.util.stream.Stream

@Component
class BreadQueryConverterImpl : BreadQueryConverter {
    override fun toQueryDto(entity: Stream<BreadDto>, last: Boolean): BreadQueryDto = BreadQueryDto(
        list = entity,
        last = last
    )

    override fun toQueryDto(entity: MutableList<BreadSize>): MutableList<BreadSizeDto> {
        var list: MutableList<BreadSizeDto> = arrayListOf()
        for(e in entity) {
            list.add(BreadSizeDto(e.id, e.size, e.extramoney))
        }
        return list
    }

}