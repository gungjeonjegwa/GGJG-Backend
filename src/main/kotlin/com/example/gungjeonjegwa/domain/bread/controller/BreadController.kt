package com.example.gungjeonjegwa.domain.bread.controller

import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadDetailQueryDto
import com.example.gungjeonjegwa.domain.bread.data.dto.BreadQueryDto
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.service.BreadService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bread")
class BreadController(
    val breadService: BreadService
) {
    @GetMapping
    fun findAllBread(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<BreadQueryDto> =
        breadService.findAllPost(PageRequest.of(page, size))
            .let { ResponseEntity.ok(it) }

    @GetMapping("/")
    fun findAllBreadByCategory(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
        @RequestParam("category") category: Category
    ): ResponseEntity<BreadQueryDto> =
        breadService.findAllPostByCategory(PageRequest.of(page,size), category)
            .let { ResponseEntity.ok(it) }

    @GetMapping("/{id}")
    fun findPostByIndex(@PathVariable("id") breadId: Long): BreadDetailQueryDto {
        return breadService.findPostByIndex(breadId)
    }
}
