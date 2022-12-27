package com.example.gungjeonjegwa

import com.example.gungjeonjegwa.domain.bread.repository.BreadSearchRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(
    excludeFilters = [ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = [BreadSearchRepository::class]
    )]
)
@ConfigurationPropertiesScan
@SpringBootApplication
class GungjeonjegwaApplication
fun main(args: Array<String>) {
    runApplication<GungjeonjegwaApplication>(*args)
}
