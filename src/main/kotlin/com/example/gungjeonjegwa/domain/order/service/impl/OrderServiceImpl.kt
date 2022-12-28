package com.example.gungjeonjegwa.domain.order.service.impl

import com.example.gungjeonjegwa.domain.bread.exception.BreadNotFoundException
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadSizeRepository
import com.example.gungjeonjegwa.domain.order.data.dto.OrderDto
import com.example.gungjeonjegwa.domain.order.data.dto.OrderListDto
import com.example.gungjeonjegwa.domain.order.data.entity.Orders
import com.example.gungjeonjegwa.domain.order.data.entity.PayOrder
import com.example.gungjeonjegwa.domain.order.data.enum.ActivityType
import com.example.gungjeonjegwa.domain.order.data.enum.ProductType
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderBuyRequest
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderListRequest
import com.example.gungjeonjegwa.domain.order.exception.AddressNotFoundException
import com.example.gungjeonjegwa.domain.order.exception.PaymentFaildException
import com.example.gungjeonjegwa.domain.order.repository.OrderRepository
import com.example.gungjeonjegwa.domain.order.repository.PayOrderRepository
import com.example.gungjeonjegwa.domain.order.service.OrderService
import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.repository.AddressRepository
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val payOrderRepository: PayOrderRepository,
    private val breadRepository: BreadRepository,
    private val breadSizeRepository: BreadSizeRepository,
    private val userUtil: UserUtil,
    private val addressRepository: AddressRepository
) : OrderService {

    override fun createOrderList(request: CreateOrderBuyRequest) {
        if(!request.isPayment) {
            throw PaymentFaildException()
        }
        val addressOptional = addressRepository.findById(request.address.id).orElseThrow { AddressNotFoundException() }
        val currentUser = userUtil.fetchCurrentUser()
        val order = Orders(
            id = generatedOrderId(),
            activity = ActivityType.WAITORDER,
            deliveryPrice = 3000,
            user = currentUser!!,
            address = addressOptional
        )
        orderRepository.save(order)
        request.list.forEach{list ->
            run {
                val bread = breadRepository.findById(list.breadId).orElseThrow { BreadNotFoundException() }
                val payOrder = PayOrder(
                    id = 0,
                    count = list.count,
                    price = list.price,
                    type = ProductType.ORDER,
                    age = list.age,
                    orders = order,
                    bread = bread,
                    breadSize = list.unit?.let {
                        breadSizeRepository.findByDetailBreadAndUnit(
                            bread.breadDetail,
                            it
                        )
                    }
                )
                payOrderRepository.save(payOrder)
            }
        }
    }

    private fun generatedOrderId(): String {
        val currentTime = System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.format(currentTime).replace("-", "")
        val random = Random()
        val number = random.nextInt(8999) + 1000
        return "B$date$number"
    }
}