package com.example.gungjeonjegwa.domain.basket.repository

import com.example.gungjeonjegwa.domain.basket.data.entity.Basket
import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface BasketRepository : JpaRepository<Basket, Long> {
    fun findByUser(user: User): List<Basket>

    fun findByIdAndUser(id: Long, user: User): Basket

    fun existsByBreadAndUserAndBreadSize(bread: Bread, user: User, breadSize: BreadSize?): Boolean
}