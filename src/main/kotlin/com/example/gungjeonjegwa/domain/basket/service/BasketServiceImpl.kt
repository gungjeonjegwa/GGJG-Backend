package com.example.gungjeonjegwa.domain.basket.service

import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto
import com.example.gungjeonjegwa.domain.basket.data.entity.Basket
import com.example.gungjeonjegwa.domain.basket.data.request.BasketCreateRequest
import com.example.gungjeonjegwa.domain.basket.exception.ExistBasketException
import com.example.gungjeonjegwa.domain.basket.exception.LessRequestDataException
import com.example.gungjeonjegwa.domain.basket.repository.BasketRepository
import com.example.gungjeonjegwa.domain.basket.util.BasketConverter
import com.example.gungjeonjegwa.domain.bread.data.entity.BreadSize
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.repository.BreadDetailRepository
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
                if(it.count > it.bread.count) {
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

    override fun plusCount(id: Long): Int {
        val currentUser = userUtil.fetchCurrentUser()
        val baskets = basketRepository.findByIdAndUser(id, currentUser!!)
        baskets.plusCount()
        return baskets.count
    }
    @Transactional
    override fun minusCount(id: Long): Int {
        val currentUser = userUtil.fetchCurrentUser()
        val baskets = basketRepository.findByIdAndUser(id, currentUser!!)
        baskets.minusCount()
        return baskets.count
    }

    override fun createBasket(basket: BasketCreateRequest) {
        val breadSize: BreadSize
        val currentUser = userUtil.fetchCurrentUser()
        val bread = breadRepository.findById(basket.breadId).orElseThrow{ RuntimeException("빵 데이터가 없습니다. ") }
        val existsByDetailBread = breadSizeRepository.existsByDetailBread(bread.breadDetail)
        if(existsByDetailBread == true) {
            if(basket.age == null || basket.size == null || basket.unit == null || basket.extramoney == null) {
                throw LessRequestDataException()
            } else {
                breadSize =
                    breadSizeRepository.findBySizeAndExtramoneyAndUnitAndDetailBread(
                        basket.size!!,
                        basket.extramoney!!,
                        basket.unit!!,
                        bread.breadDetail
                    )
                basketRepository.save(Basket(0, basket.age, basket.count, bread, currentUser!!, breadSize))
            }
        }
        if(basket.age == null || basket.size == null || basket.unit == null || basket.extramoney == null) {
            basketRepository.save(Basket(0, null, basket.count, bread, currentUser!!, null))
        }
    }
}