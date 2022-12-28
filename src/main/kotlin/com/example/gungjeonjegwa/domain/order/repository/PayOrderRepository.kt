package com.example.gungjeonjegwa.domain.order.repository

import com.example.gungjeonjegwa.domain.order.data.entity.PayOrder
import org.springframework.data.jpa.repository.JpaRepository

interface PayOrderRepository : JpaRepository<PayOrder, Long> {
}