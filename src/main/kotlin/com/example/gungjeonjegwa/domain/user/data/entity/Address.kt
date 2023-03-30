package com.example.gungjeonjegwa.domain.user.data.entity

import com.example.gungjeonjegwa.domain.order.data.entity.Orders
import com.example.gungjeonjegwa.global.entity.BaseTimeEntity
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var zipCode: Long,

    var roadName: String,

    var landNumber: String,

    var detailAddress: String?,

    var typeBasic: Boolean,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    val ordersList: MutableList<Orders> = arrayListOf()
) : BaseTimeEntity() {
}