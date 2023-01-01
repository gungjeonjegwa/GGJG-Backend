package com.example.gungjeonjegwa.domain.user.data.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Stamp(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,


    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) {
}