package com.example.gungjeonjegwa.domain.bread.data.entity

import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.delivery.data.entity.SellDeliveryType
import org.springframework.data.elasticsearch.annotations.Document
import javax.persistence.*

@Entity
@Document(indexName = "bread")
class Bread(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    @Column(nullable = true)
    val price: Long?,

    @Enumerated(EnumType.STRING)
    val category: Category,

    val count: Long,

    val isSoldOut: Boolean,

    val previewUrl: String,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bread")
    val breadDetail: BreadDetail,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bread")
    val likeItem: MutableList<LikeItem> = ArrayList(),

    @OneToMany(mappedBy = "bread",fetch = FetchType.LAZY)
    val sellDeliveryType: MutableList<SellDeliveryType> = ArrayList()
) {
}