package com.example.gungjeonjegwa.domain.user.service.impl

import com.example.gungjeonjegwa.domain.user.data.entity.DeviceToken
import com.example.gungjeonjegwa.domain.user.repository.DeviceRepository
import com.example.gungjeonjegwa.domain.user.service.DeviceService
import com.example.gungjeonjegwa.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class DeviceServiceImpl(
    private val deviceRepository: DeviceRepository,
    private val userUtil: UserUtil
) : DeviceService {

    override fun createDeviceToken(deviceToken: String) {
        val currentUser = userUtil.fetchCurrentUser()
        currentUser
            ?.let { deviceRepository.save(DeviceToken(currentUser.id, currentUser, deviceToken)) }
    }
}