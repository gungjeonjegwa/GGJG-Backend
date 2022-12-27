package com.example.gungjeonjegwa.domain.bread.service.impl

import com.example.gungjeonjegwa.domain.bread.data.request.AdminCreateBreadRequest
import com.example.gungjeonjegwa.domain.bread.repository.BreadRepository
import com.example.gungjeonjegwa.domain.bread.service.AdminBreadService
import com.example.gungjeonjegwa.global.util.UserUtil

class AdminBreadServiceImpl(
    private val breadRepository: BreadRepository,
    private val userUtil: UserUtil
) : AdminBreadService {
    override fun createBread(req: AdminCreateBreadRequest) {
    }
}