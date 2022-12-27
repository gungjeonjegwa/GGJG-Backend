package com.example.gungjeonjegwa.domain.bread.repository.impl

import com.example.gungjeonjegwa.domain.bread.data.entity.Bread
import com.example.gungjeonjegwa.domain.bread.repository.CustomBreadSearchRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Repository
import java.util.stream.Collectors

@Repository
class CustomBreadSearchRepositoryImpl(
    private val elasticsearchOperations: ElasticsearchOperations
) : CustomBreadSearchRepository {

    override fun searchByTitle(title: String, pageable: Pageable): List<Bread> {
        val criteria: Criteria = Criteria.where("bread.title").contains(title)
        val query: Query = CriteriaQuery(criteria).setPageable<Query>(pageable)
        val search: SearchHits<Bread> = elasticsearchOperations!!.search(query, Bread::class.java)
        return search.stream()
            .map { obj: SearchHit<Bread> -> obj.content }
            .collect(Collectors.toList())

    }
}