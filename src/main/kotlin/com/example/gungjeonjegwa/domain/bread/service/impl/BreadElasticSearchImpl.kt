package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadLikeDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadSearchDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.LikeItem
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.service.BreadElasticSearch
import com.example.gungjeonjegwa.domain.bread.util.BreadConverter
import com.example.gungjeonjegwa.domain.bread.repository.BreadSearchRepository
import com.example.gungjeonjegwa.domain.bread.repository.LikeItemRepository
import com.example.gungjeonjegwa.domain.bread.service.BreadService
import com.example.gungjeonjegwa.domain.bread.util.BreadQueryConverter
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class BreadElasticSearchImpl(
    private val breadConverter: BreadConverter,
    private val breadRepository: BreadRepository,
    private val likeItemRepository: LikeItemRepository,
    private val userUtil: UserUtil,
    private val breadQueryConverter: BreadQueryConverter,
) : BreadElasticSearch {
    override fun relationSearchByTitle(title: String): MutableList<BreadLikeDto> {
        val currentUser = userUtil.fetchCurrentUser()
        // userSearchRepository.findByBasicProfile_NameContains(name) 가능relationSearchByTitle
        val likeItems: MutableList<LikeItem> = likeItemRepository.findAllByUser(currentUser)
        val breadDtoList = breadRepository.findByTitleContaining(title)
            .map { bread: Bread -> breadConverter.toDto(bread) }
        return breadQueryConverter.toQueryDto(addLikeItemActivity(breadDtoList, likeItems))

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
    private fun addLikeItemActivity(bread: List<BreadDto>, likeItem: MutableList<LikeItem>): MutableList<BreadLikeDto> {
        val list: MutableList<BreadLikeDto> = mutableListOf()
        var isEnabled: Boolean = false
        for (b in bread) {
            for(i in likeItem) {
                isEnabled = false
                if(b.id == i.bread.id) {
                    isEnabled = true
                    list.add(breadConverter.toDto(b, true))
                    break
                }
            }
            if(!isEnabled) {
                list.add(breadConverter.toDto(b, false))
            }
        }
        return list
    }
}