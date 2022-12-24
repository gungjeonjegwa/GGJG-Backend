package com.example.gungjeonjegwa.domain.bread.util

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadLikeDto
import com.example.gungjeonjegwa.domain.bread.data.dto.LikeItemDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import com.example.gungjeonjegwa.domain.bread.data.entity.LikeItem

interface BreadConverter {
    fun toDto(entity: Bread): BreadDto

    fun toDto(entity: BreadDetail, id: Long): BreadDetailDto

    fun toDto(entity: BreadDto, isLike: Boolean): BreadLikeDto

}