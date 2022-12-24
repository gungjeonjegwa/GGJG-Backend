package com.example.gungjeonjegwa.domain.bread.data.dto

import java.util.stream.Stream

class BreadQueryDto(
    val list: MutableList<BreadLikeDto>,

    val last: Boolean
) {
}