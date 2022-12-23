package com.example.gungjeonjegwa.domain.user.repository

import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
}