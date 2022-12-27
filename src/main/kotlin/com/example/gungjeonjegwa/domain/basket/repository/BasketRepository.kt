package com.example.gungjeonjegwa.domain.basket.repository

import com.example.gungjeonjegwa.domain.basket.data.entity.Basket
import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface BasketRepository : JpaRepository<Basket, Long> {
    fun findByUser(user: User): List<Basket>
}