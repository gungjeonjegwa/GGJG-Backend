package com.example.gungjeonjegwa.domain.order.service.impl

import com.example.gungjeonjegwa.domain.basket.repository.BasketRepository
import com.example.gungjeonjegwa.domain.bread.exception.BreadNotFoundException
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadSizeRepository
import com.example.gungjeonjegwa.domain.order.data.dto.*
import com.example.gungjeonjegwa.domain.order.data.entity.Orders
import com.example.gungjeonjegwa.domain.order.data.entity.PayOrder
import com.example.gungjeonjegwa.domain.order.data.enum.ActivityType
import com.example.gungjeonjegwa.domain.order.data.request.CreateOrderBuyRequest
import com.example.gungjeonjegwa.domain.order.exception.DefaultAddressNotFoundException
import com.example.gungjeonjegwa.domain.order.exception.OrderIdNotFoundException
import com.example.gungjeonjegwa.domain.order.exception.PaymentFaildException
import com.example.gungjeonjegwa.domain.order.repository.OrderRepository
import com.example.gungjeonjegwa.domain.order.repository.PayOrderRepository
import com.example.gungjeonjegwa.domain.order.service.OrderService
import com.example.gungjeonjegwa.domain.user.data.dto.AddressDto
import com.example.gungjeonjegwa.domain.user.data.entity.Address
import com.example.gungjeonjegwa.domain.user.repository.AddressRepository
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val payOrderRepository: PayOrderRepository,
    private val breadRepository: BreadRepository,
    private val breadSizeRepository: BreadSizeRepository,
    private val userUtil: UserUtil,
    private val addressRepository: AddressRepository,
    private val basketRepository: BasketRepository
) : OrderService {

    @Transactional
    override fun createOrderList(request: CreateOrderBuyRequest) {
        val currentUser = userUtil.fetchCurrentUser()
        if(!request.address.isBasic) { // 기본 배송지가 아니라면
            val address = Address(
                0,
                request.address.zipCode,
                request.address.roadName,
                request.address.landNumber,
                request.address.detailAddress,
                request.address.isBasic,
                currentUser!!
            )
            val existsAddress: Boolean = addressRepository.existsByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(request.address.zipCode,
                request.address.roadName,
                request.address.landNumber,
                request.address.detailAddress,
                currentUser!!,
                request.address.isBasic)
            if(!existsAddress) {
                addressRepository.save(address)
            }
            val order = orderRepository.findById(request.orderId).orElseThrow { OrderIdNotFoundException() }
            if(!request.isPayment) {
                orderRepository.delete(order)
                throw PaymentFaildException()
            }
            order.address = address
            orderRepository.save(order)
            request.list.forEach{
                val bread = breadRepository.findById(it.breadId).orElseThrow { BreadNotFoundException() }
                val breadSize = breadSizeRepository.findByDetailBreadAndUnit(bread.breadDetail, it.unit)
                bread.count -= it.count
                val payOrder = PayOrder(
                    id = 0,
                    count = it.count,
                    price = it.price,
                    age = it.age,
                    orders = order,
                    bread = bread,
                    breadSize = breadSize,
                )
                payOrderRepository.save(payOrder)
                val existsBasket = basketRepository.existsByBreadAndUserAndBreadSize(bread, currentUser, breadSize)
                if(existsBasket) {
                    val basket =
                        basketRepository.findByBreadAndBreadSizeAndUser(bread, breadSize, currentUser)
                    basketRepository.delete(basket)
                }
            }
        } else {
            val order = orderRepository.findById(request.orderId).orElseThrow { OrderIdNotFoundException() }
            if(!request.isPayment) {
                orderRepository.delete(order)
                throw PaymentFaildException()
            }
            val address = addressRepository.findByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(
                request.address.zipCode,
                request.address.roadName,
                request.address.landNumber,
                request.address.detailAddress,
                currentUser!!,
                request.address.isBasic,
            )
            if(!(address?.zipCode == request.address.zipCode && address.roadName == request.address.roadName &&
                address?.landNumber == request.address.landNumber && address.detailAddress == request.address.detailAddress && address.typeBasic == request.address.isBasic)) {
                throw DefaultAddressNotFoundException()
            }
            order.address = address
            orderRepository.save(order)
            request.list.forEach{
                val bread = breadRepository.findById(it.breadId).orElseThrow { BreadNotFoundException() }
                bread.count -= it.count
                val breadSize = breadSizeRepository.findByDetailBreadAndUnit(bread.breadDetail, it.unit)
                val payOrder = PayOrder(
                    id = 0,
                    count = it.count,
                    price = it.price,
                    age = it.age,
                    orders = order,
                    bread = bread,
                    breadSize = breadSize,
                )
                payOrderRepository.save(payOrder)
                val existsBasket = basketRepository.existsByBreadAndUserAndBreadSize(bread, currentUser, breadSize)
                if(existsBasket) {
                    val basket =
                        basketRepository.findByBreadAndBreadSizeAndUser(bread, breadSize, currentUser)
                    basketRepository.delete(basket)
                }

            }
        }
    }

    override fun selectBuyOrder(): OrderListDto {
        val currentUser = userUtil.fetchCurrentUser()
        var addRessDto: AddressDto? = null
        val addressList = addressRepository.findAllByUser(currentUser!!)
        addressList.forEach{
            if(it.typeBasic) {
                addRessDto = AddressDto(
                    zipCode = it.zipCode,
                    roadName = it.roadName,
                    landNumber = it.landNumber,
                    detailAddress = it.detailAddress,
                    isBasic = it.typeBasic
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

    override fun findMyOrderList(): MutableList<MyOrderList>{ // 500 에러 for 문 에러
        val currentUser = userUtil.fetchCurrentUser()
        val list: MutableList<MyOrderList> = mutableListOf()
        for((index, value) in currentUser!!.orders.withIndex()) {
            if(value.payOrder.isEmpty()) continue
            list.add(
                MyOrderList(
                    orderId = value.id,
                    activityType = value.activity,
                    title = value.payOrder[0].bread.title,
                    price = value.payOrder[0].bread.price,
                    previewUrl = value.payOrder[0].bread.previewUrl,
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
                previewUrl = it.bread.previewUrl,
                extraMoney = it.breadSize?.extramoney,
                price = it.price,
                count = it.count
            ))
        }
        val addressDto = AddressDto(
            zipCode = order.address!!.zipCode,
            roadName = order.address!!.roadName,
            landNumber = order.address!!.landNumber,
            detailAddress = order.address?.detailAddress,
            isBasic = order.address!!.typeBasic
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