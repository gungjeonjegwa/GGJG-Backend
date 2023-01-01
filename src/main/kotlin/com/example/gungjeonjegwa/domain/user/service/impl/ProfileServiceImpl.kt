package com.example.gungjeonjegwa.domain.user.service.impl

import com.example.gungjeonjegwa.domain.coupon.repository.MyCouponRepository
import com.example.gungjeonjegwa.domain.order.repository.OrderRepository
import com.example.gungjeonjegwa.domain.user.data.response.MyProfileResponse
import com.example.gungjeonjegwa.domain.user.repository.StampRepository
import com.example.gungjeonjegwa.domain.user.service.ProfileService
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val orderRepository: OrderRepository,
    private val myProfileRepository: MyCouponRepository,
    private val stampRepository: StampRepository,
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
}