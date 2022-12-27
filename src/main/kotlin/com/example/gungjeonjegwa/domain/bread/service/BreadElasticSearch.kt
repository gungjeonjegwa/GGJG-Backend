package com.example.gungjeonjegwa.domain.bread.service

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import org.springframework.data.domain.Pageable

interface BreadElasticSearch {

    fun searchByTitle(title: String): List<BreadDto>
}