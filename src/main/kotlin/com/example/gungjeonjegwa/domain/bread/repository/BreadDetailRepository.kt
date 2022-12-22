package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BreadDetailRepository : JpaRepository<BreadDetail, Long> {
    fun findByBread(bread: Bread): Optional<BreadDetail>

}