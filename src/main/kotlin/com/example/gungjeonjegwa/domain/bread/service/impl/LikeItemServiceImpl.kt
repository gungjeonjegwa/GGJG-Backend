package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.dto.LikeItemActivityResponse
import com.example.gungjeonjegwa.domain.bread.data.entity.LikeItem
import com.example.gungjeonjegwa.domain.bread.exception.BreadNotFoundException
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.LikeItemRepository
import com.example.gungjeonjegwa.domain.bread.service.LikeItemService
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class LikeItemServiceImpl(
    private val userUtil: UserUtil,
    private val breadRepository: BreadRepository,
    private val likeItemRepository: LikeItemRepository
) : LikeItemService {
    override fun activityLikeItem(breadId: Long): LikeItemActivityResponse {
        val currentUser = userUtil.fetchCurrentUser()
        val bread = breadRepository.findById(breadId).orElseThrow { BreadNotFoundException() }
        val existsLikeItem = likeItemRepository.existsByUserAndBread(currentUser, bread)
        if(existsLikeItem) {
            val likeItem = likeItemRepository.findByUserAndBread(currentUser, bread)
            likeItemRepository.delete(likeItem)
            return LikeItemActivityResponse(false)
        }
        val likeItem = LikeItem(0, bread, currentUser!!)
        likeItemRepository.save(likeItem)
        return LikeItemActivityResponse(true)
    }
}