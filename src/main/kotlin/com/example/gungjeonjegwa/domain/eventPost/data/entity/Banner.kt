package com.example.gungjeonjegwa.domain.eventPost.data.entity

import com.example.gungjeonjegwa.global.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Banner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val previewUrl: String,

    @Column(nullable = true)
    val webUrl: String?,

    val position: Long
) : BaseTimeEntity() {
}