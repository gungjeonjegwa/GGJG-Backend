package com.example.gungjeonjegwa.domain.user.service

import com.example.gungjeonjegwa.domain.user.data.response.MyProfileResponse

interface ProfileService {
    fun myProfileInfo(): MyProfileResponse
}