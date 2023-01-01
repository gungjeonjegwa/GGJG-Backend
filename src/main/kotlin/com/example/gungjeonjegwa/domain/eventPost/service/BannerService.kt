package com.example.gungjeonjegwa.domain.eventPost.service

import com.example.gungjeonjegwa.domain.eventPost.data.entity.Banner
import com.example.gungjeonjegwa.domain.eventPost.repository.BannerRepository
import org.springframework.stereotype.Service

@Service
class BannerService(
    private val bannerRepository: BannerRepository
) {
    fun findAllBanner(): MutableList<Banner> {
        val banners = bannerRepository.findAllByOrderByPositionAsc()
        return banners
    }
}