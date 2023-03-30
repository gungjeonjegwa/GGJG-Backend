package com.example.gungjeonjegwa.domain.user.data.entity

import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
@DynamicUpdate
class DeviceToken (
    @Id
    val userId: String,

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    val user: User,

    val token: String,
)