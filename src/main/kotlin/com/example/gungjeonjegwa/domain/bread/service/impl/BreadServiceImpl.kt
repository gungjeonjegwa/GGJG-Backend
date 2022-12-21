package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailQueryDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.repository.BreadDetailRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadSizeRepository
import com.example.gungjeonjegwa.domain.bread.repository.SellDeliveryTypeRepository
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
    val sellDeliveryTypeRepository: SellDeliveryTypeRepository,
    val breadSizeRepository: BreadSizeRepository,
    val breadConverter: BreadConverter,
    val breadQueryConverter: BreadQueryConverter
) : BreadService {
    @Transactional
    override fun findAllPost(pagination: PageRequest): BreadQueryDto {
        val findBy = breadRepository.findBy(pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(findBy.get(), findBy.isLast)
    }
    @Transactional
    override fun findAllPostByCategory(pagination: PageRequest, category: Category): BreadQueryDto {
        val findByCategory = breadRepository.findByCategory(category, pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(findByCategory.get(), findByCategory.isLast)
    }

    @Transactional
    override fun findPostByIndex(id: Long): BreadDetailQueryDto =
        breadDetailRepository.findById(id)
            .orElseThrow { RuntimeException("hi") }
            .let { breadConverter.toDto(it) to it }
            .let { breadSizeRepository.findAllByDetailBread(it.second) to it.first }
            .let { breadQueryConverter.toQueryDto(it.first) to it.second }
            .let { breadQueryConverter.toQueryDto(it.first, it.second) }
}