package com.example.gungjeonjegwa.domain.user.repository

import com.example.gungjeonjegwa.domain.user.data.entity.DeviceToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface DeviceRepository : CrudRepository<DeviceToken, UUID> {
}