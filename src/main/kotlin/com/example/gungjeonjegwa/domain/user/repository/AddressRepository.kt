package com.example.gungjeonjegwa.domain.user.repository

import com.example.gungjeonjegwa.domain.user.data.entity.Address
import com.example.gungjeonjegwa.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
    fun findAllByUser(user: User): List<Address>

    fun findAllByUserOrderByCreatedAtDesc(user: User): List<Address>

    fun findByUserAndTypeBasic(user: User, typebasic: Boolean): Address?

    fun existsByUserAndTypeBasic(user: User, typebasic: Boolean): Boolean
    fun existsByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(zipCode: Long, roadName: String, landNumber: String, detailAddress: String?, user: User, typebasic: Boolean): Boolean

    fun findByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(zipCode: Long, roadName: String, landNumber: String, detailAddress: String?, user: User, typebasic: Boolean): Address?

    fun findByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUser(zipCode: Long, roadName: String, landNumber: String, detailAddress: String?, user: User): Address?

    fun findAllByUserAndTypeBasicOrderByCreatedAtDesc(user: User, typebasic: Boolean): MutableList<Address>
}