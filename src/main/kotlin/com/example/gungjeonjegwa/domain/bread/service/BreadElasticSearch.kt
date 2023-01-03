package com.example.gungjeonjegwa.domain.bread.service

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadLikeDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadSearchDto

interface BreadElasticSearch {
    fun relationSearchByTitle(title: String): MutableList<BreadLikeDto>

    fun searchByTitle(title: String): MutableList<BreadSearchDto>
}