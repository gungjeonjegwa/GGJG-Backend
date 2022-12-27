package com.example.gungjeonjegwa.domain.basket.controller

import com.example.gungjeonjegwa.domain.basket.data.dto.BasketDto
import com.example.gungjeonjegwa.domain.basket.service.BasketService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/basket")
class BasketController(
    private val basketService: BasketService
) {
    @GetMapping
    fun findBasket(): List<BasketDto> {
        return basketService.findBasketByUser()
    }

    @DeleteMapping("{id}")
    fun deleteBasket(@PathVariable() id: Long){
        return basketService.deleteBasketByUser(id)
    }
}