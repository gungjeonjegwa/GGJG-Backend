package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.BreadDetail
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import org.springframework.data.jpa.repository.JpaRepository

interface BreadSizeRepository : JpaRepository<BreadSize, Long> {
    fun findAllByDetailBread(id: BreadDetail): MutableList<BreadSize>
    fun findBySizeAndExtramoneyAndUnitAndDetailBread(size: String, extramoney: Long, unit: String, detailBread: BreadDetail): BreadSize?

    fun existsByDetailBread(detailBread: BreadDetail): Boolean
}