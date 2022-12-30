package com.example.gungjeonjegwa.domain.user.util.impl

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.dto.AddressLatelyDto
import com.example.gungjeonjegwa.domain.user.data.entity.Address
import com.example.gungjeonjegwa.domain.user.data.entity.User
import com.example.gungjeonjegwa.domain.user.util.AddressConverter
import org.springframework.stereotype.Component

@Component
class AdressConverterImpl() : AddressConverter {
    override fun toEntity(address: AddressDto, user: User): Address = Address(
        id = 0,
        zipCode = address.zipCode,
        roadName = address.roadName,
        landNumber = address.landNumber,
        detailAddress = address.detailAddress,
        typeBasic = address.isBasic,
        user = user
    )

    override fun toEntity(address: AddressLatelyDto, user: User): Address = Address(
        id = 0,
        zipCode = address.zipCode,
        roadName = address.roadName,
        landNumber = address.landNumber,
        detailAddress = address.detailAddress,
        typeBasic = false,
        user = user
    )
}