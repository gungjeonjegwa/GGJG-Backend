package com.example.gungjeonjegwa.domain.bread.repository

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface BreadSearchRepository : ElasticsearchRepository<Bread, Long>, CustomBreadSearchRepository {
    fun findByTitleContaining(title: String): List<Bread>
}