package com.example.gungjeonjegwa.domain.order.data.entity

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import javax.persistence.*

@Entity
class PayOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val count: Long,

    val price: Long,

    @Column(nullable = true)
    val age: Long?,

    @ManyToOne
    @JoinColumn(name = "order_id")
    val orders: Orders,

    @ManyToOne
    @JoinColumn(name = "bread_id")
    val bread: Bread,

    @ManyToOne
    @JoinColumn(name = "breadsize_id", nullable = true)
    val breadSize: BreadSize?
) {
}