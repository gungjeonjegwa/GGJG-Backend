package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadSearchDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.service.BreadElasticSearch
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import com.example.gungjeonjegwa.domain.bread.repository.BreadSearchRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class BreadElasticSearchImpl(
    private val breadConverter: BreadConverter,
    private val breadRepository: BreadRepository,
) : BreadElasticSearch {
    override fun relationSearchByTitle(title: String): List<BreadDto> {
        // userSearchRepository.findByBasicProfile_NameContains(name) 가능relationSearchByTitle
        return breadRepository.findByTitleContaining(title)
            .stream()
            .map { bread: Bread -> breadConverter.toDto(bread) }
            .collect(Collectors.toList())
    }

    override fun searchByTitle(title: String): MutableList<BreadSearchDto> {
        val containingBread = breadRepository.findByTitleContaining(title)
            .stream()
            .map { bread: Bread -> breadConverter.toSearchDto(bread) }
            .collect(Collectors.toList())
        return containingBread
//        val searchBreadList: MutableSet<String> = mutableSetOf()
//        val breadSearchDto: MutableList<BreadSearchDto> = mutableListOf()
//        for(containData in containingBread) {
//            val array = containData.title.split(" ")
//            for(searchDate in array) {
//                searchBreadList.add(searchDate)
//            }
//        }
//        for(breadSearch in searchBreadList) {
//            breadSearchDto.add(BreadSearchDto(breadSearch))
//        }
//        return breadSearchDto


    }
}