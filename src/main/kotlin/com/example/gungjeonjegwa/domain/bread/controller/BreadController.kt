package com.example.gungjeonjegwa.domain.bread.controller

import com.example.gungjeonjegwa.domain.bread.data.dto.*
import com.example.gungjeonjegwa.domain.bread.data.enum.Category
import com.example.gungjeonjegwa.domain.bread.service.BreadElasticSearch
import com.example.gungjeonjegwa.domain.bread.service.BreadService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bread")
class BreadController(
    val breadService: BreadService,
    val breadElasticSearch: BreadElasticSearch
) {
    @GetMapping
    fun findAllBread(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<BreadQueryDto> =
        breadService.findAllPost(PageRequest.of(page, size))
            .let { ResponseEntity.ok(it) }

    @GetMapping("/kind")
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

    @GetMapping("/relationsearch")
    fun search(@RequestParam("title") title: String): MutableList<BreadLikeDto>{
        return breadElasticSearch.relationSearchByTitle(title)
    }

    @GetMapping("/search")
    fun relationSearch(@RequestParam("title") title: String): MutableList<BreadSearchDto> {
        return breadElasticSearch.searchByTitle(title)
    }
}
