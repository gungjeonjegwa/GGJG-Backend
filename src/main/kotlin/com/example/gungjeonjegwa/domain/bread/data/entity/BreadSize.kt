package com.example.gungjeonjegwa.domain.bread.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class BreadSize(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val size: String,

    val extramoney: Long,

    val unit: String,

    @ManyToOne
    @JoinColumn(name = "detailbread_id")
    val detailBread: BreadDetail
) {
}