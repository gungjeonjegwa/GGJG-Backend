package com.example.gungjeonjegwa.global.configuration.security.auth

import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
    private val user: User
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority?> = user.roles

    override fun getPassword(): String? = null

    override fun getUsername(): String = user.id

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false
}