package com.example.gungjeonjegwa.domain.bread.data.entity

import com.example.gungjeonjegwa.domain.user.data.entity.User
import javax.persistence.*

@Entity
class LikeItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_id")
    val bread: Bread,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) {
}