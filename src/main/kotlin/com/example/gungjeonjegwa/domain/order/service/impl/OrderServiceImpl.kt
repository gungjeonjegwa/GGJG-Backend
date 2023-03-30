package com.example.gungjeonjegwa.domain.order.service.impl

import com.example.gungjeonjegwa.domain.basket.data.entity.Basket
import com.example.gungjeonjegwa.domain.basket.repository.BasketRepository
import com.example.gungjeonjegwa.domain.basket.service.BasketServiceImpl
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import com.example.gungjeonjegwa.domain.bread.exception.BreadNotFoundException
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadSizeRepository
import com.example.gungjeonjegwa.domain.coupon.exception.CouponNotEnabledException
import com.example.gungjeonjegwa.domain.coupon.exception.MyCouponNotFoundException
import com.example.gungjeonjegwa.domain.coupon.repository.MyCouponRepository
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
import com.example.gungjeonjegwa.domain.user.exception.LatelyAddressException
import com.example.gungjeonjegwa.domain.user.repository.AddressRepository
import com.example.gungjeonjegwa.domain.user.service.AddressService
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val payOrderRepository: PayOrderRepository,
    private val breadRepository: BreadRepository,
    private val breadSizeRepository: BreadSizeRepository,
    private val userUtil: UserUtil,
    private val addressRepository: AddressRepository,
    private val basketRepository: BasketRepository,
    private val myCouponRepository: MyCouponRepository,
) : OrderService {

    @Transactional
    override fun createOrderList(request: CreateOrderBuyRequest) {
        println(request.address.toString())
        val currentUser = userUtil.fetchCurrentUser()
        if(!request.address.isBasic) { // 기본 배송지가 아니라면
            val order = orderRepository.findById(request.orderId).orElseThrow { OrderIdNotFoundException() }
            if(!request.isPayment) {
                orderRepository.delete(order)
                throw PaymentFaildException()
            }
            println("1")
            val address = addressRepository.findByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(
                request.address.zipCode,
                request.address.roadName,
                request.address.landNumber,
                request.address.detailAddress,
                currentUser!!,
                request.address.isBasic
            )
            println("2 " + address)
            if(!(address?.zipCode == request.address.zipCode && address.roadName == request.address.roadName &&
                        address?.landNumber == request.address.landNumber && address.detailAddress == request.address.detailAddress && address.typeBasic == request.address.isBasic)) {
                throw LatelyAddressException()
            }
            order.address = address
            request.list.forEach{
                val bread = breadRepository.findById(it.breadId).orElseThrow { BreadNotFoundException() }
                bread.count -= it.count
                if(bread.count <= 0) {
                    bread.isSoldOut = true
                }
                if(it.couponId != null) {
                    val myCoupons = myCouponRepository.findAllByUser(currentUser)
                    for(coupon in myCoupons) {
                        if(it.couponId!! == coupon.id) {
                            coupon.isUsed = true
                        }
                    }
                }
                if(it.unit == null) {
                    val payOrder = PayOrder(
                        id = 0,
                        count = it.count,
                        price = it.price,
                        discountPrice = it.discountPrice,
                        age = it.age,
                        orders = order,
                        bread = bread,
                        breadSize = null,
                    )
                    payOrderRepository.save(payOrder)
                    val existsBasket = basketRepository.existsByBreadAndUserAndBreadSize(bread, currentUser, null)
                    if(existsBasket) {
                        val basket =
                            basketRepository.findByBreadAndBreadSizeAndUser(bread, null, currentUser)
                        basketRepository.delete(basket)
                    }
                } else {
                    val breadSize = breadSizeRepository.findByDetailBreadAndUnit(bread.breadDetail, it.unit)
                    val payOrder = PayOrder(
                        id = 0,
                        count = it.count,
                        price = it.price,
                        discountPrice = it.discountPrice,
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
        } else {
            val order = orderRepository.findById(request.orderId).orElseThrow { OrderIdNotFoundException() }
            if(!request.isPayment) {
                orderRepository.delete(order)
                throw PaymentFaildException()
            }
            println("1")
            val address = addressRepository.findByZipCodeAndRoadNameAndLandNumberAndDetailAddressAndUserAndTypeBasic(
                request.address.zipCode,
                request.address.roadName,
                request.address.landNumber,
                request.address.detailAddress,
                currentUser!!,
                request.address.isBasic,
            )
            println("2")
            if(!(address?.zipCode == request.address.zipCode && address.roadName == request.address.roadName &&
                address?.landNumber == request.address.landNumber && address.detailAddress == request.address.detailAddress && address.typeBasic == request.address.isBasic)) {
                throw DefaultAddressNotFoundException()
            }
            println("3 ${address.toString()}")
            order.address = address
            request.list.forEach{
                val bread = breadRepository.findById(it.breadId).orElseThrow { BreadNotFoundException() }
                bread.count -= it.count
                if(bread.count <= 0) {
                    bread.isSoldOut = true
                }
                if(it.couponId != null) {
                    val myCoupons = myCouponRepository.findAllByUser(currentUser)
                    for(coupon in myCoupons) {
                        if(it.couponId!! == coupon.id) {
                            coupon.isUsed = true
                        }
                    }

                }
                if(it.unit == null) {
                    val payOrder = PayOrder(
                        id = 0,
                        count = it.count,
                        price = it.price,
                        discountPrice = it.discountPrice,
                        age = it.age,
                        orders = order,
                        bread = bread,
                        breadSize = null,
                    )
                    payOrderRepository.save(payOrder)
                    val existsBasket = basketRepository.existsByBreadAndUserAndBreadSize(bread, currentUser, null)
                    if(existsBasket) {
                        val basket =
                            basketRepository.findByBreadAndBreadSizeAndUser(bread, null, currentUser)
                        basketRepository.delete(basket)
                    }
                } else {
                    val breadSize = breadSizeRepository.findByDetailBreadAndUnit(bread.breadDetail, it.unit)
                    val payOrder = PayOrder(
                        id = 0,
                        count = it.count,
                        price = it.price,
                        discountPrice = it.discountPrice,
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
    }

    override fun selectBuyOrder(): OrderListDto {
        val currentUser = userUtil.fetchCurrentUser()
        var addressDto: AddressDto? = null
        val addressList = addressRepository.findAllByUser(currentUser!!)
        addressList.forEach{
            if(it.typeBasic) {
                addressDto = AddressDto(
                    zipCode = it.zipCode,
                    roadName = it.roadName,
                    landNumber = it.landNumber,
                    detailAddress = it.detailAddress,
                    isBasic = true
                )
            }
        }
        return OrderListDto(name = currentUser.name, phone = currentUser.phone, address = addressDto)
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
        var price: Long = 0
        var text: String = ""
        for(orders in currentUser!!.orders) {
            price = 0
            if(orders.payOrder.isEmpty()) continue
            if(orders.payOrder.size <= 1) {
                text = ""
            }
            else if(orders.payOrder.size >= 2) {
                val sizes: Int = orders.payOrder.size - 1
                text = " 외 ${sizes}개"
            }
            for(payOrders in orders.payOrder) {
                if(payOrders.discountPrice == null) {
                    price += payOrders.price
                } else {
                    price += payOrders.discountPrice!!
                }
            }
            list.add(
                MyOrderList(
                    orderId = orders.id,
                    activityType = orders.activity,
                    title = orders.payOrder[0].bread.title + text,
                    price = price + 3000,
                    previewUrl = orders.payOrder[0].bread.previewUrl,
                    createdDate = orders.createdAt
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
                discountPrice = it.discountPrice,
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

    private fun useCoupon(mycouponId: Long) {
        val mycoupon = myCouponRepository.findById(mycouponId).orElseThrow{ MyCouponNotFoundException() }
        if(!mycoupon.coupon.isEnabledCoupon) {
            throw CouponNotEnabledException()
        }
        if(LocalDateTime.now().isAfter(mycoupon.coupon.finishDate)) {

        }

    }
}