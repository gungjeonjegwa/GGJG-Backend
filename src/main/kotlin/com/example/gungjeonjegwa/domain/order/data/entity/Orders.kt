package com.example.gungjeonjegwa.domain.order.data.entity

import com.example.gungjeonjegwa.domain.order.data.enum.ActivityType
import com.example.gungjeonjegwa.domain.user.data.entity.Address
import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.global.entity.BaseTimeEntity
import javax.persistence.*

@Entity
class Orders(
    @Id
    val id: String,

    @Enumerated(EnumType.STRING)
    val activity: ActivityType,

    val deliveryPrice: Long,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders", cascade = [CascadeType.REMOVE])
    val payOrder: MutableList<PayOrder> = ArrayList(),

    @ManyToOne
    @JoinColumn(name = "address_id")
    var address: Address?,
) : BaseTimeEntity() {
}