package com.example.gungjeonjegwa.domain.bread.controller

import com.example.gungjeonjegwa.domain.bread.data.dto.LikeItemActivityResponse
import com.example.gungjeonjegwa.domain.bread.service.LikeItemService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bread/likeitem")
class LikeItemController(
    private val likeItemService: LikeItemService
) {
    @PostMapping("/{breadId}")
    fun activityLikeItem(@PathVariable("breadId") breadId: Long): LikeItemActivityResponse {
        return likeItemService.activityLikeItem(breadId)
    }
}