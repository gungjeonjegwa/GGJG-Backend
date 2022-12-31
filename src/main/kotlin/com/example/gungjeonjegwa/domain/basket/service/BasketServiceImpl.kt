package com.example.gungjeonjegwa.domain.basket.service

import com.example.gungjeonjegwa.domain.basket.data.CountResponse
import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto
import com.example.gungjeonjegwa.domain.basket.data.entity.Basket
import com.example.gungjeonjegwa.domain.basket.data.request.BasketCreateRequest
import com.example.gungjeonjegwa.domain.basket.exception.*
import com.example.gungjeonjegwa.domain.basket.repository.BasketRepository
import com.example.gungjeonjegwa.domain.basket.util.BasketConverter
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.repository.BreadSizeRepository
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BasketServiceImpl(
    private val userUtil: UserUtil,
    private val basketRepository: BasketRepository,
    private val basketConverter: BasketConverter,
    private val breadRepository: BreadRepository,
    private val breadSizeRepository: BreadSizeRepository,
) : BasketService{
    @Transactional
    override fun findBasketByUser(): List<BasketDto> {
        val currentUser = userUtil.fetchCurrentUser()
        val baskets = basketRepository.findByUser(currentUser!!)
            .map {
                if(it.count >= it.bread.count) {
                    it.count = it.bread.count.toInt()
                    basketConverter.toDto(it, it.bread.count.toInt())
                }
                else {
                    basketConverter.toDto(it)
                }
            }

        return baskets
    }

    override fun deleteBasketByUser(id: Long) {
        val currentUser = userUtil.fetchCurrentUser()
        val baskets = basketRepository.findByUser(currentUser!!)
            .filter { it.id == id }
        basketRepository.deleteAll(baskets)
    }
    @Transactional
    override fun plusCount(id: Long): CountResponse {
        val currentUser = userUtil.fetchCurrentUser()
        val baskets = basketRepository.findByIdAndUser(id, currentUser!!) ?: throw ExistNotBasketException()
        if(baskets.count >= baskets.bread.count) {
            throw OverCountException()
        }
        baskets.plusCount()
        return CountResponse(baskets.count)
    }
    @Transactional
    override fun minusCount(id: Long): CountResponse {
        val currentUser = userUtil.fetchCurrentUser()
        val baskets = basketRepository.findByIdAndUser(id, currentUser!!) ?: throw BasketNotFoundException()
        baskets.minusCount()
        return CountResponse(baskets.count)
    }

    override fun createBasket(basket: List<BasketCreateRequest>) {
        val currentUser = userUtil.fetchCurrentUser()
        basket.forEach{basket ->
            run {
                val bread = breadRepository.findAllById(basket.breadId)
                if (bread.category == Category.CAKE) {
                    val sizes: BreadSize? =
                        breadSizeRepository.findBySizeAndExtramoneyAndUnitAndDetailBread(
                            basket.size!!,
                            basket.extramoney!!,
                            basket.unit!!,
                            bread.breadDetail
                        )
                    val existsByBasket =
                        basketRepository.existsByBreadAndUserAndBreadSize(bread, currentUser!!, sizes)
                    if (existsByBasket == true) {
                        throw ExistBasketException()
                    }
                } else {
                    val breadAndUser = basketRepository.existsByBreadAndUser(bread, currentUser!!)
                    if (breadAndUser == true) {
                        throw ExistBasketException()
                    }
                }
                val existsByDetailBread = breadSizeRepository.existsByDetailBread(bread.breadDetail)
                if (existsByDetailBread == true) {
                    if (basket.size == null || basket.unit == null || basket.extramoney == null) {
                        throw LessRequestDataException()
                    } else {
                        val breadSize: BreadSize? =
                            breadSizeRepository.findBySizeAndExtramoneyAndUnitAndDetailBread(
                                basket.size!!,
                                basket.extramoney!!,
                                basket.unit!!,
                                bread.breadDetail
                            )
                        basketRepository.save(Basket(0, basket.age, basket.count, bread, currentUser!!, breadSize))
                    }
                } else if (basket.age == null || basket.size == null || basket.unit == null || basket.extramoney == null) {
                    basketRepository.save(Basket(0, null, basket.count, bread, currentUser!!, null))
                }
            }
        }
    }
}