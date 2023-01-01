package com.example.gungjeonjegwa.domain.user.service.impl

import com.example.gungjeonjegwa.domain.coupon.repository.MyCouponRepository
import com.example.gungjeonjegwa.domain.order.repository.OrderRepository
import com.example.gungjeonjegwa.domain.order.repository.PayOrderRepository
import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.entity.Address
import com.example.gungjeonjegwa.domain.user.data.response.MyProfileResponse
import com.example.gungjeonjegwa.domain.user.data.response.PrivateResponse
import com.example.gungjeonjegwa.domain.user.exception.StampNotTenPicesException
import com.example.gungjeonjegwa.domain.user.repository.AddressRepository
import com.example.gungjeonjegwa.domain.user.repository.StampRepository
import com.example.gungjeonjegwa.domain.user.service.ProfileService
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val orderRepository: OrderRepository,
    private val myProfileRepository: MyCouponRepository,
    private val payOrderRepository: PayOrderRepository,
    private val stampRepository: StampRepository,
    private val addressRepository: AddressRepository,
    private val userUtil: UserUtil
) : ProfileService {
    override fun myProfileInfo(): MyProfileResponse {
        val currentUser = userUtil.fetchCurrentUser()
        var waitorder: Long = 0
        var deliverying: Long = 0
        var completedDelivery: Long = 0
        var cancel: Long = 0
        var returnMoney: Long = 0
        val orders = orderRepository.findAllByUser(currentUser!!)
        orders.forEach { // WAITORDER, DELIVERYING, COMPLETEDELIVERY, WAITCANCEL, CANCEL, WAITRETURN, RETURN
            val existsPayOrder: Boolean = payOrderRepository.existsByOrders(it)
            if(existsPayOrder) {
                when(it.activity.name) {
                    "WAITORDER" -> waitorder++
                    "DELIVERYING" -> deliverying++
                    "COMPLETEDELIVERY" -> completedDelivery++
                    "WAITCANCEL" -> cancel++
                    "CANCEL" -> cancel++
                    "WAITRETURN" -> returnMoney++
                    "RETURN" -> returnMoney++
                }
            }

        }
        val couponSize = myProfileRepository.findAllByUser(currentUser!!).size
        val stampCount = stampRepository.findAllByUser(currentUser!!).size
        return MyProfileResponse(
            name = currentUser!!.name,
            waitOrderCount = waitorder,
            inDeliverCount = deliverying,
            completeDelivery = completedDelivery,
            cancelCount = cancel,
            returnCount = returnMoney,
            couponCount = couponSize.toLong(),
            stampCount = stampCount.toLong()
        )
    }

    override fun getPrivacyInfo(): PrivateResponse {
        val currentUser = userUtil.fetchCurrentUser()
        var phoneNumber: String? = ""
        if(currentUser!!.phone == null) {
            phoneNumber = null
        } else {
            phoneNumber = currentUser!!.phone?.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        }
        val address: MutableList<AddressDto?> = mutableListOf()
        val addressList = addressRepository.findAllByUser(currentUser).forEach {
            if(it.typeBasic) {
                address.add(AddressDto(
                    zipCode = it.zipCode,
                    roadName = it.roadName,
                    landNumber = it.landNumber,
                    detailAddress = it.detailAddress,
                    isBasic = true
                ))
            } else {
                address.add(null)
            }
        }
        return PrivateResponse(
            name = currentUser!!.name,
            id = currentUser!!.id,
            email = currentUser!!.email,
            phone = phoneNumber,
            address = address[0]
        )
    }

    override fun deleteStamp() {
        val currentUser = userUtil.fetchCurrentUser()
        val stampsSize = stampRepository.findAllByUser(currentUser!!)
        if(stampsSize.size < 10) {
            throw StampNotTenPicesException()
        }
        stampRepository.deleteAll(stampsSize)
    }
}