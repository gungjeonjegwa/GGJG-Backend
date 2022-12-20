package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.delivery.data.entity.SellDeliveryType
import org.springframework.data.jpa.repository.JpaRepository

interface SellDeliveryTypeRepository : JpaRepository<SellDeliveryType, Long> {
}