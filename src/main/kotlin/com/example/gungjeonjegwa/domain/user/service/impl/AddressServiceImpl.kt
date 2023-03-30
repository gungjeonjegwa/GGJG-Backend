package com.example.gungjeonjegwa.domain.user.service.impl

import com.example.gungjeonjegwa.domain.order.exception.AddressNotFoundException
import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.dto.AddressLatelyDto
import com.example.gungjeonjegwa.domain.user.data.entity.Address
import com.example.gungjeonjegwa.domain.user.exception.ExistsDefaultAddressException
import com.example.gungjeonjegwa.domain.user.repository.AddressRepository
import com.example.gungjeonjegwa.domain.user.service.AddressService
import com.example.gungjeonjegwa.domain.user.util.AddressConverter
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.data.repository.findByIdOrNull
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
            val addressOrderByCreatedAtAsc = addressRepository.findAllByUserOrderByCreatedAtDesc(currentUser) // Address 총 조회
            val orderByCreatedAtAsc = addressOrderByCreatedAtAsc.filter { !it.typeBasic }
            val latelySize = orderByCreatedAtAsc.size
            
            addressOrderByCreatedAtAsc
                .forEach {
                    if (it.zipCode == address.zipCode) {
                        if (it.typeBasic) {
                            if (it.detailAddress != address.detailAddress) {
                                it.typeBasic = false
                                val newAddressEntity = addressConverter.toEntity(address, currentUser)
                                addressRepository.save(newAddressEntity)
                                if (latelySize >= 5)
                                    addressRepository.delete(orderByCreatedAtAsc[4])
                            }
                            return
                        }
                    }
                }
            val addressTypeBasicEntity = addressRepository.findByUserAndTypeBasic(currentUser, true) ?: throw AddressNotFoundException()
            addressTypeBasicEntity.typeBasic = false
            val newAddressEntity = addressConverter.toEntity(address, currentUser)
            addressRepository.save(newAddressEntity)
            if (latelySize >= 5)
                addressRepository.delete(orderByCreatedAtAsc[4])
        }
    }

    override fun getLatelyAddress(): List<AddressDto> {
        val currentUser = userUtil.fetchCurrentUser()
        return if (currentUser != null) {
            addressRepository.findAllByUserOrderByCreatedAtDesc(currentUser)
                .map { AddressDto(it.zipCode, it.roadName, it.landNumber, it.detailAddress, it.typeBasic) }
                .filter { !it.isBasic }
        } else {
            listOf()
        }
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
                    currentUser,
                    address.isBasic
                )
            if (existsAddress) {
                return
            } else {
                if (latelySize >= 5) {
                    val orderByCreatedAtAsc = addressRepository.findAllByUserOrderByCreatedAtDesc(currentUser)
                        .filter { !it.typeBasic }
                    addressRepository.delete(orderByCreatedAtAsc[4])
                    val addressEntity = addressConverter.toEntity(address, currentUser)
                    addressRepository.save(addressEntity)
                    return
                }
                if (
                    defaultAddress.zipCode == address.zipCode
                    && defaultAddress.roadName == address.roadName
                    && defaultAddress.landNumber == address.landNumber
                    && defaultAddress.detailAddress == address.detailAddress
                ) {
                    throw ExistsDefaultAddressException()
                }
                val addressEntity = addressConverter.toEntity(address, currentUser)
                addressRepository.save(addressEntity)
            }
        }
    }
}