package com.example.gungjeonjegwa.domain.bread.service

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import org.springframework.data.domain.PageRequest


interface BreadService {
    fun findAllPost(pagination: PageRequest): BreadQueryDto
