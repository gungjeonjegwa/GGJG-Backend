package com.example.gungjeonjegwa.domain.user.service

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto

interface AddressService {
    fun createDefaultAddress(address: AddressDto)
}