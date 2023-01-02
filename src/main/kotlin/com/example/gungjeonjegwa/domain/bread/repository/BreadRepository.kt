package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository

interface BreadRepository : JpaRepository<Bread, Long> {
    fun findAllById(id: Long): Bread
    fun findBy(pagination: PageRequest): Page<Bread>

    fun findAllByCategory(category: Category, pagination: PageRequest): Page<Bread>

    fun findByTitleContaining(title: String): List<Bread>
}