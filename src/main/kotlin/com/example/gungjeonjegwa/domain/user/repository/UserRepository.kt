package com.example.gungjeonjegwa.domain.user.repository

import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface UserRepository : JpaRepository<User, String> {
    fun existsByEmail(email: String): Boolean
}