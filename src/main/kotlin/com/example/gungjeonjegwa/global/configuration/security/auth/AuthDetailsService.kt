package com.example.gungjeonjegwa.global.configuration.security.auth

import com.example.gungjeonjegwa.domain.user.exception.UserNotFoundException
import com.example.gungjeonjegwa.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findById(username!!).orElseThrow{ UserNotFoundException() }
        return AuthDetails(user)
    }
}
