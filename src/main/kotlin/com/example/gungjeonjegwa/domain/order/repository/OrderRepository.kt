package com.example.gungjeonjegwa.domain.order.repository

import com.example.gungjeonjegwa.domain.order.data.entity.Orders
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Orders, String> {
}