package com.example.gungjeonjegwa.domain.user.data.entity

import com.example.gungjeonjegwa.domain.bread.data.entity.LikeItem
import com.example.gungjeonjegwa.domain.user.data.enum.UserRole
import javax.persistence.*

@Entity
class User(
    @Id
    val id: String,

    val password: String,

    val name: String,

    val phone: String,

    val email: String,

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "UserRole", joinColumns = [JoinColumn(name = "id")])
    val roles: MutableList<UserRole> = mutableListOf(),

    @Column(nullable = true)
    var refreshtoken: String?,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val likeItem: MutableList<LikeItem> = ArrayList()
) {
    fun updateRefreshToken(refreshtoken: String?) {
        this.refreshtoken = refreshtoken
    }
}