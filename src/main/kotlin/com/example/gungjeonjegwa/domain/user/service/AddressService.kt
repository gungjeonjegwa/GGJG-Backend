package com.example.gungjeonjegwa.domain.user.service

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.dto.AddressLatelyDto

interface AddressService {
    fun createDefaultAddress(address: AddressDto)

    fun getLatelyAddress(): List<AddressDto>

    fun latelyAddress(address: AddressLatelyDto)
}