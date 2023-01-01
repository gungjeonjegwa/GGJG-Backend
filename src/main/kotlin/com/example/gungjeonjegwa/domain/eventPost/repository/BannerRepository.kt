package com.example.gungjeonjegwa.domain.eventPost.repository

import com.example.gungjeonjegwa.domain.eventPost.data.entity.Banner
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<Banner, Long> {
    fun findAllByOrderByPositionAsc(): MutableList<Banner>
}