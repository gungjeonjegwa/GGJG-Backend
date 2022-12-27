package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import org.springframework.data.domain.Pageable

interface CustomBreadSearchRepository {
    fun searchByTitle(title: String, pageable: Pageable): List<Bread>
}