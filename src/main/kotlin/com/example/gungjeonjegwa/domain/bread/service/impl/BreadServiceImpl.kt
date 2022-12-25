package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailQueryDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadLikeDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import com.example.gungjeonjegwa.domain.bread.data.entity.LikeItem
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.repository.*
import com.example.gungjeonjegwa.domain.bread.service.BreadService
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import com.example.gungjeonjegwa.domain.bread.util.BreadQueryConverter
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.data.domain.Page
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
    val breadImageRepository: BreadImageRepository,
    val userUtil: UserUtil,
    val likeItemRepository: LikeItemRepository
) : BreadService {
    @Transactional(readOnly = true)
    override fun findAllPost(pagination: PageRequest): BreadQueryDto {
        val user = userUtil.fetchCurrentUser()
        val likeItems: MutableList<LikeItem>
        if(user == null) {
            likeItems = likeItemRepository.findAllByUser(user)
        } else {
            likeItems = likeItemRepository.findAllByUser(user)
        }
        val bread = breadRepository.findBy(pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(addLikeItemActivity(bread, likeItems), bread.isLast)
    }
    @Transactional(readOnly = true)
    override fun findAllPostByCategory(pagination: PageRequest, category: Category): BreadQueryDto {
        val user = userUtil.fetchCurrentUser()
        val likeItems: MutableList<LikeItem>
        if(user == null) {
            likeItems = likeItemRepository.findAllByUser(user)
        } else {
            likeItems = likeItemRepository.findAllByUser(user)
        }
        val findByCategory = breadRepository.findAllByCategory(category, pagination)
            .map { breadConverter.toDto(it) }
        return breadQueryConverter.toQueryDto(addLikeItemActivity(findByCategory, likeItems), findByCategory.isLast)
    }

    @Transactional(readOnly = true)
    override fun findPostByIndex(id: Long): BreadDetailQueryDto {
        val user = userUtil.fetchCurrentUser()
        val breadDetail: BreadDetail
        val bread = breadRepository.findById(id).orElseThrow{ BreadNotFoundException() }
        val breadDetailEntity= breadDetailRepository.findByBread(bread)
            .orElseThrow{ RuntimeException("빵 세부사항 없음")}
            .also { breadDetail = it }
            .let { breadConverter.toDto(it, id) to it }
            .let { breadSizeRepository.findAllByDetailBread(it.second) to it.first }
            .let { breadQueryConverter.toBreadSizeDto(it.first) to it.second }
        return breadImageRepository.findByBreadDetail(breadDetail)
            .let { breadQueryConverter.toBreadImageDto(it) }
            .let { likeItemRepository.existsByUserAndBread(user, bread) to it }
            .let {
                if(it.first == true) {
                    true to it.second
                }
                else {
                    false to it.second
                }
            }
            .let { breadQueryConverter.toQueryDto(breadDetailEntity.first, breadDetailEntity.second, it.second, it.first) }
    }

    private fun addLikeItemActivity(bread: Page<BreadDto>, likeItem: MutableList<LikeItem>): MutableList<BreadLikeDto> {
        val list: MutableList<BreadLikeDto> = mutableListOf()
        for (b in bread) {
            for(i in likeItem) {
                if(b.id == i.bread.id) {
                    list.add(breadConverter.toDto(b, true))
                    break
                }
            }
            list.add(breadConverter.toDto(b, false))
        }
        return list
    }
}