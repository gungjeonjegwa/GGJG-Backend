package com.example.gungjeonjegwa.domain.bread.service

import com.example.gungjeonjegwa.domain.bread.data.dto.LikeItemActivityResponse

interface LikeItemService {
    fun activityLikeItem(breadId: Long): LikeItemActivityResponse
}