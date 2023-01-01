package com.example.gungjeonjegwa.domain.user.data.response

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.entity.Address

class PrivateResponse(
    val name: String,

    val id: String,

    val email: String,

    val phone: String?,

    val address: AddressDto?
) {
}