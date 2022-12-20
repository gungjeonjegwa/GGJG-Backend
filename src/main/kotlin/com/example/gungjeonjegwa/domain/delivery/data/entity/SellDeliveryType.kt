package com.example.gungjeonjegwa.domain.delivery.data.entity

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.delivery.data.enum.SellType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class SellDeliveryType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val sellType: SellType,

    @ManyToOne
    @JoinColumn(name = "bread_id")
    val bread: Bread
) {
}