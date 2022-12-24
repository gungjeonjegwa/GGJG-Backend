package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.LikeItem
import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface LikeItemRepository : JpaRepository<LikeItem, Long> {
    fun findAllByUser(entity: User): MutableList<LikeItem>

    fun existsByUserAndBread(entity: User, bread: Bread): Boolean
}