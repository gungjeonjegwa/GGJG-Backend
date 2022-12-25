package com.example.gungjeonjegwa.domain.bread.util

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadLikeDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail

interface BreadConverter {
    fun toDto(entity: Bread): BreadDto

    fun toDto(entity: BreadDetail, id: Long, title: String): BreadDetailDto

    fun toDto(entity: BreadDto, isLike: Boolean): BreadLikeDto

}