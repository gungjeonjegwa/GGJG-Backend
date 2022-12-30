package com.example.gungjeonjegwa.domain.user.util

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.dto.AddressLatelyDto
import com.example.gungjeonjegwa.domain.user.data.entity.Address
import com.example.gungjeonjegwa.domain.user.data.entity.User

interface AddressConverter {
    fun toEntity(address: AddressDto, user: User): Address
    fun toEntity(address: AddressLatelyDto, user: User): Address
}