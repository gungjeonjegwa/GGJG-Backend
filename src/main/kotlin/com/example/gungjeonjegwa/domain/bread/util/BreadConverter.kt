package com.example.gungjeonjegwa.domain.bread.util

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDto
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread

interface BreadConverter {
    fun toDto(entity: Bread): BreadDto
}