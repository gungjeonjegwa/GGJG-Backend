package com.example.gungjeonjegwa.domain.user.data.entity

import com.example.gungjeonjegwa.domain.order.data.entity.Orders
import javax.persistence.*

@Entity
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val zipCode: String,

    val roadName: String,

    val landNumber: String,

    val detailAddress: String?,

    val isBasic: Boolean,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    val ordersList: MutableList<Orders> = arrayListOf()
) {
}