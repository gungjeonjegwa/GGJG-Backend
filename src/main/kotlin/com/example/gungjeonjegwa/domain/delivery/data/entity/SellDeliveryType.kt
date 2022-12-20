package com.example.gungjeonjegwa.domain.delivery.data.entity

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.delivery.data.enum.SellType
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class SellDeliveryType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Enumerated(EnumType.STRING)
    val sellType: SellType,

    @ManyToOne
    @JoinColumn(name = "bread_id")
    @JsonIgnore
    val bread: Bread
) {
}