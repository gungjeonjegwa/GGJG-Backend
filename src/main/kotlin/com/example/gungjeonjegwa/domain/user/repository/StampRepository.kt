package com.example.gungjeonjegwa.domain.user.repository

import com.example.gungjeonjegwa.domain.user.data.entity.Stamp
import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface StampRepository : JpaRepository<Stamp, Long> {
    fun findAllByUser(user: User): MutableList<Stamp>
}