package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.service.BreadElasticSearch
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import com.example.gungjeonjegwa.domain.bread.repository.BreadSearchRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class BreadElasticSearchImpl(
    private val breadSearchRepository: BreadSearchRepository,
    private val breadConverter: BreadConverter
) : BreadElasticSearch {
    override fun searchByTitle(title: String): List<BreadDto> {
        // userSearchRepository.findByBasicProfile_NameContains(name) 가능
        return breadSearchRepository!!.findByTitleContaining(title)
            .stream()
            .map { bread: Bread -> breadConverter.toDto(bread) }
            .collect(Collectors.toList())
    }
}