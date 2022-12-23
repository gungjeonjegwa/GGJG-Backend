package com.example.gungjeonjegwa.global.util

import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.domain.user.repository.UserRepository
import com.example.gungjeonjegwa.global.configuration.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class UserUtil(
    private val userRepository: UserRepository
) {
    fun fetchCurrentUser(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val userId = if(principal is UserDetails) {
            (principal as AuthDetails).username
        } else {
            principal.toString()
        }
        return fetchUserByEmail(userId)
    }

    fun fetchUserByEmail(userId: String): User =
        userRepository.findById(userId).orElseThrow{ RuntimeException("유저를 찾을수 없습니다.") }
}