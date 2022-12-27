package com.example.gungjeonjegwa.domain.basket.service

import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto
import com.example.gungjeonjegwa.domain.basket.repository.BasketRepository
import com.example.gungjeonjegwa.domain.basket.util.BasketConverter
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class BasketServiceImpl(
    private val userUtil: UserUtil,
    private val basketRepository: BasketRepository,
    private val basketConverter: BasketConverter
) : BasketService{
    override fun findBasketByUser(): List<BasketDto> {
        val currentUser = userUtil.fetchCurrentUser()
        val baskets = basketRepository.findByUser(currentUser!!)
            .map { basketConverter.toDto(it) }
        return baskets
    }

//    override fun deleteBasketByUser(id: Long) {
//        val currentUser = userUtil.fetchCurrentUser()
//        basketRepository.findByUser(currentUser!!)
//    }
}