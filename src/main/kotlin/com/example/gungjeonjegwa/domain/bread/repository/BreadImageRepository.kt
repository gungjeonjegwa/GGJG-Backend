package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadImage
import org.springframework.data.jpa.repository.JpaRepository

interface BreadImageRepository : JpaRepository<BreadImage, Long> {
    fun findByBreadDetail(breadDetail: BreadDetail): MutableList<BreadImage>
}