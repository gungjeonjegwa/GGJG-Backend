package com.example.gungjeonjegwa.domain.user.service.impl

import com.example.gungjeonjegwa.domain.order.exception.AddressNotFoundException
import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.dto.AddressLatelyDto
import com.example.gungjeonjegwa.domain.user.exception.ExistsDefaultAddressException
import com.example.gungjeonjegwa.domain.user.repository.AddressRepository
import com.example.gungjeonjegwa.domain.user.service.AddressService
import com.example.gungjeonjegwa.domain.user.util.AddressConverter
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddressServiceImpl(
    private val userUtil: UserUtil,
    private val addressRepository: AddressRepository,
    private val addressConverter: AddressConverter
) : AddressService {
    @Transactional
    override fun createDefaultAddress(address: AddressDto) {
        val currentUser = userUtil.fetchCurrentUser()
        val existsAddress =
            addressRepository.existsByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(
                address.zipCode,
                address.roadName,
                address.landNumber,
                address.detailAddress,
                currentUser!!,
                address.isBasic
            )
        val basic = addressRepository.existsByUserAndTypeBasic(currentUser, address.isBasic) // 해당 유저 기본 값 있는지
        if(!existsAddress && !basic) { // 기본 배송지 생성 basic : false
            val addressEntity = addressConverter.toEntity(address, currentUser)
            addressRepository.save(addressEntity)
        } else {
            val addressEntity = addressRepository.findByUserAndTypeBasic(currentUser, address.isBasic) ?: throw AddressNotFoundException()
            addressEntity.zipCode = address.zipCode
            addressEntity.roadName = address.roadName
            addressEntity.landNumber = address.landNumber
            addressEntity.detailAddress = address.detailAddress
            addressEntity.typeBasic = address.isBasic
        }
    }

    override fun getLatelyAddress(): List<AddressDto> {
        val currentUser = userUtil.fetchCurrentUser()
        return addressRepository.findAllByUserOrderByCreatedAtDesc(currentUser!!)
            .map { AddressDto(it.zipCode, it.roadName, it.landNumber, it.detailAddress, it.typeBasic) }
            .filter { !it.isBasic }
    }

    @Transactional
    override fun latelyAddress(address: AddressLatelyDto) {
        if (!address.isBasic) {
            val currentUser = userUtil.fetchCurrentUser()
            val userAddress = addressRepository.findAllByUser(currentUser!!)
            val latelySize = userAddress
                .filter { !it.typeBasic }
                .size
            val defaultAddress = userAddress
                .filter { it.typeBasic }[0]
            val existsAddress =
                addressRepository.existsByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(
                    address.zipCode,
                    address.roadName,
                    address.landNumber,
                    address.detailAddress,
                    currentUser!!,
                    address.isBasic
                )
            if (existsAddress) {
                return
            } else {
                if (latelySize >= 5) {
                    val orderByCreatedAtAsc =
                        addressRepository.findAllByUserAndTypeBasicOrderByCreatedAtDesc(currentUser, false)
                    addressRepository.delete(orderByCreatedAtAsc[4])
                    val addressEntity = addressConverter.toEntity(address, currentUser)
                    addressRepository.save(addressEntity)
                    return
                }
                if (defaultAddress.zipCode == address.zipCode && defaultAddress.roadName == address.roadName && defaultAddress.landNumber == address.landNumber && defaultAddress.detailAddress == address.detailAddress) {
                    throw ExistsDefaultAddressException()
                }
                val addressEntity = addressConverter.toEntity(address, currentUser)
                addressRepository.save(addressEntity)
            }
        }
    }
}