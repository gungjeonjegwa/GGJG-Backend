package com.example.gungjeonjegwa.global.configuration.elasticsearch

import com.example.gungjeonjegwa.domain.bread.repository.BreadSearchRepository
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@EnableElasticsearchRepositories(basePackageClasses = [BreadSearchRepository::class])
@Configuration
class ElasticSearchConfig(
    var elasticSearchProperties: ElasticSearchProperties
) : AbstractElasticsearchConfiguration() {

    override fun elasticsearchClient(): RestHighLevelClient {
        val clientConfiguration: ClientConfiguration = ClientConfiguration.builder()
            .connectedTo(elasticSearchProperties.getHostAndPort())
            .build()
        return RestClients.create(clientConfiguration).rest()
    }
}