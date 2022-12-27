package com.example.gungjeonjegwa.domain.basket.data.entity

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import com.example.gungjeonjegwa.domain.user.data.entity.User
import javax.persistence.*

@Entity
class Basket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = true)
    val age: Long? = null,

    val count: Long,

    @ManyToOne
    @JoinColumn(name = "bread_id", nullable = true)
    val bread: Bread,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "breadsize_id", nullable = true)
    val breadSize: BreadSize? = null

) {
}