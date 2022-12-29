package com.example.gungjeonjegwa.domain.order.service.impl

import com.example.gungjeonjegwa.domain.bread.exception.BreadNotFoundException
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadSizeRepository
import com.example.gungjeonjegwa.domain.order.data.dto.*
import com.example.gungjeonjegwa.domain.order.data.entity.Orders
import com.example.gungjeonjegwa.domain.order.data.entity.PayOrder
import com.example.gungjeonjegwa.domain.order.data.enum.ActivityType
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderBuyRequest
import com.example.gungjeonjegwa.domain.order.exception.AddressNotFoundException
import com.example.gungjeonjegwa.domain.order.exception.OrderIdNotFoundException
import com.example.gungjeonjegwa.domain.order.exception.PaymentFaildException
import com.example.gungjeonjegwa.domain.order.repository.OrderRepository
import com.example.gungjeonjegwa.domain.order.repository.PayOrderRepository
import com.example.gungjeonjegwa.domain.order.service.OrderService
import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.repository.AddressRepository
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.zip
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

    @Transactional
    override fun createOrderList(request: CreateOrderBuyRequest) {
        val addressOptional = addressRepository.findById(request.address.id).orElseThrow { AddressNotFoundException() }
        val currentUser = userUtil.fetchCurrentUser()
        val order = orderRepository.findById(request.orderId).orElseThrow { OrderIdNotFoundException() }
        order.address = addressOptional
        if(!request.isPayment) {
            orderRepository.delete(order)
            throw PaymentFaildException()
        }
        request.list.forEach{list ->
            run {
                val bread = breadRepository.findById(list.breadId).orElseThrow { BreadNotFoundException() }
                val payOrder = PayOrder(
                    id = 0,
                    count = list.count,
                    price = list.price,
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

    override fun selectBuyOrder(): OrderListDto {
        val currentUser = userUtil.fetchCurrentUser()
        var addRessDto: AddressDto? = null
        val addressList = addressRepository.findAllByUser(currentUser!!)
        addressList.forEach{
            if(it.isBasic) {
                addRessDto = AddressDto(
                    id = it.id,
                    zipCode = it.zipCode,
                    roadName = it.roadName,
                    landNumber = it.landNumber,
                    detailAddress = it.detailAddress,
                    isBasic = it.isBasic
                )
            }
        }
        return OrderListDto(name = currentUser!!.name, phone = currentUser!!.phone, address = addRessDto)
    }


    override fun createOrderId(): OrderId {
        val currentUser = userUtil.fetchCurrentUser()
        val order = Orders(
            id = generatedOrderId(),
            activity = ActivityType.WAITORDER,
            deliveryPrice = 3000,
            user = currentUser!!,
            address = null
        )
        return orderRepository.save(order).id
            .let { OrderId(orderId =  it) }
    }

    override fun findMyOrderList(): MutableList<MyOrderList>{
        val currentUser = userUtil.fetchCurrentUser()
        val list: MutableList<MyOrderList> = mutableListOf()
        for((index, value) in currentUser!!.orders.withIndex()) {
            list.add(
                MyOrderList(
                    orderId = value.id,
                    activityType = value.activity,
                    title = value.payOrder[index].bread.title,
                    price = value.payOrder[index].bread.price,
                    preview_url = value.payOrder[index].bread.previewUrl,
                    createdDate = value.createdAt
                ))
        }
        return list;
    }

    override fun findMyDetailOrder(orderId: String): MyOrderDetailListDto {
        val currentUser = userUtil.fetchCurrentUser()
        val order = orderRepository.findById(orderId).orElseThrow { OrderIdNotFoundException() }
        val list: MutableList<MyOrderDetailDto> = mutableListOf()
        order.payOrder.forEach {
            list.add(MyOrderDetailDto(
                title = it.bread.title,
                age = it.age,
                unit = it.breadSize?.unit,
                size = it.breadSize?.size,
                extraMoney = it.breadSize?.extramoney,
                price = it.price,
                count = it.count
            ))
        }
        val addressDto = AddressDto(
            id = order.address!!.id,
            zipCode = order.address!!.zipCode,
            roadName = order.address!!.roadName,
            landNumber = order.address!!.landNumber,
            detailAddress = order.address?.detailAddress,
            isBasic = order.address!!.isBasic
        )
        return MyOrderDetailListDto(
            address = addressDto,
            orderId = order.id,
            name = order.user.name,
            phone = order.user.phone,
            list = list
        )
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