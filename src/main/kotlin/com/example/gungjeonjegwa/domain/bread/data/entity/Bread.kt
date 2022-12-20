package com.example.gungjeonjegwa.domain.bread.data.entity

import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.delivery.data.entity.SellDeliveryType
import javax.persistence.*

@Entity
class Bread(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val price: Long,

    @Enumerated(EnumType.STRING)
    val category: Category,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bread")
    val breadDetail: BreadDetail,

    @OneToMany(mappedBy = "bread",fetch = FetchType.LAZY)
    val sellDeliveryType: MutableList<SellDeliveryType> = ArrayList()
) {
}