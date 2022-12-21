package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailQueryDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.repository.BreadDetailRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadImageRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadSizeRepository
import com.example.gungjeonjegwa.domain.bread.service.BreadService
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import com.example.gungjeonjegwa.domain.bread.util.BreadQueryConverter
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BreadServiceImpl(
    val breadRepository: BreadRepository,
    val breadDetailRepository: BreadDetailRepository,
    val breadSizeRepository: BreadSizeRepository,
    val breadConverter: BreadConverter,
    val breadQueryConverter: BreadQueryConverter,
    val breadImageRepository: BreadImageRepository
) : BreadService {
    @Transactional
    override fun findAllPost(pagination: PageRequest): BreadQueryDto {
        val findBy = breadRepository.findBy(pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(findBy.get(), findBy.isLast)
    }
    override fun findAllPostByCategory(pagination: PageRequest, category: Category): BreadQueryDto {
        val findByCategory = breadRepository.findAllByCategory(category, pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(findByCategory.get(), findByCategory.isLast)
    }

    @Transactional(readOnly = true)
    override fun findPostByIndex(id: Long): BreadDetailQueryDto {
        val breadDetail: BreadDetail
        val bread = breadDetailRepository.findById(id)
            .orElseThrow { RuntimeException("hi") }
            .also { breadDetail = it }
            .let { breadConverter.toDto(it) to it }
            .let { breadSizeRepository.findAllByDetailBread(it.second) to it.first }
            .let { breadQueryConverter.toBreadSizeDto(it.first) to it.second }
        return breadImageRepository.findByBreadDetail(breadDetail)
            .let { breadQueryConverter.toBreadImageDto(it)}
            .let { breadQueryConverter.toQueryDto(bread.first, bread.second, it) }
    }
}