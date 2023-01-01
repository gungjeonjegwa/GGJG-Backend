package com.example.gungjeonjegwa.domain.user.service

import com.example.gungjeonjegwa.domain.user.data.response.MyProfileResponse
import com.example.gungjeonjegwa.domain.user.data.response.PrivateResponse

interface ProfileService {
    fun myProfileInfo(): MyProfileResponse

    fun getPrivacyInfo(): PrivateResponse
}