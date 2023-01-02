package com.example.gungjeonjegwa.domain.bread.service

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadSearchDto

interface BreadElasticSearch {
    fun relationSearchByTitle(title: String): List<BreadDto>

    fun searchByTitle(title: String): List<BreadSearchDto>
}