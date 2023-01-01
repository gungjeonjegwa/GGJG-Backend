package com.example.gungjeonjegwa.domain.order.repository

import com.example.gungjeonjegwa.domain.order.data.entity.Orders
import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Orders, String> {
    fun findAllByUser(user: User): MutableList<Orders>
}