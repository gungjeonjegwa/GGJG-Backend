package com.example.gungjeonjegwa.domain.user.controller

import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.dto.AddressLatelyDto
import com.example.gungjeonjegwa.domain.user.service.AddressService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users/address")
class AddressController(
    private val addressService: AddressService
) {
    @PostMapping("/basic")
    fun basicDelivery(@RequestBody address: AddressDto) {
        return addressService.createDefaultAddress(address)
    }

    @PostMapping("/lately")
    fun latelyDeliveryAddress(@RequestBody address: AddressLatelyDto) {
        return addressService.latelyAddress(address)
    }

    @GetMapping
    fun getLatelyAddress(): List<AddressDto> {
        return addressService.getLatelyAddress()
    }
}