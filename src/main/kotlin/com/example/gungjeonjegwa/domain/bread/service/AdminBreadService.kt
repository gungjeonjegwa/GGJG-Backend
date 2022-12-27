package com.example.gungjeonjegwa.domain.bread.service

import com.example.gungjeonjegwa.domain.bread.data.request.AdminCreateBreadRequest

interface AdminBreadService {
    fun createBread(req: AdminCreateBreadRequest)
}