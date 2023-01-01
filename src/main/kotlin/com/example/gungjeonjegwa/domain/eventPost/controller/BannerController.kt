package com.example.gungjeonjegwa.domain.eventPost.controller

import com.example.gungjeonjegwa.domain.eventPost.data.entity.Banner
import com.example.gungjeonjegwa.domain.eventPost.service.BannerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bread/banner")
class BannerController(
    private val bannerService: BannerService
) {
    @GetMapping
    fun findAllBanner(): MutableList<Banner> {
        return bannerService.findAllBanner()
    }
}