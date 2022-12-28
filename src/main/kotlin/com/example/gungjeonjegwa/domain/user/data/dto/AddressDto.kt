package com.example.gungjeonjegwa.domain.user.data.dto

class AddressDto(
    val id: Long,

    val zipCode: String,

    val roadName: String,

    val landNumber: String,

    val detailAddress: String?,

    val isBasic: Boolean,
) {
}