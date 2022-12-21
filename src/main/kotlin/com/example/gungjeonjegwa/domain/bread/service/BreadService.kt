package com.example.gungjeonjegwa.domain.bread.service

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailQueryDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import org.springframework.data.domain.PageRequest


interface BreadService {
    fun findAllPost(pagination: PageRequest): BreadQueryDto
    fun findAllPostByCategory(pagination: PageRequest, category: Category): BreadQueryDto
    fun findPostByIndex(id: Long): BreadDetailQueryDto
}