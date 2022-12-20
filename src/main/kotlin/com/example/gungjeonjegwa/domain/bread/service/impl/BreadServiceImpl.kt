package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.service.BreadService
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import com.example.gungjeonjegwa.domain.bread.util.BreadQueryConverter
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BreadServiceImpl(
    val breadRepository: BreadRepository,
    val breadConverter: BreadConverter,
    val breadQueryConverter: BreadQueryConverter
) : BreadService {
    override fun findAllPost(pagination: PageRequest): BreadQueryDto {
        val findBy = breadRepository.findBy(pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(findBy.get(), findBy.isLast)
    }

    override fun findAllPostByCategory(pagination: PageRequest, category: Category): BreadQueryDto {
        val findByCategory = breadRepository.findByCategory(category, pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(findByCategory.get(), findByCategory.isLast)
    }


