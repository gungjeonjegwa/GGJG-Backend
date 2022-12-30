package com.example.gungjeonjegwa.domain.user.data.dto

class AddressDto(
    val zipCode: Long,

    val roadName: String,

    val landNumber: String,

    val detailAddress: String?,

    val isBasic: Boolean,
) {
}