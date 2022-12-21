package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import org.springframework.data.jpa.repository.JpaRepository

interface BreadDetailRepository : JpaRepository<BreadDetail, Long> {
}