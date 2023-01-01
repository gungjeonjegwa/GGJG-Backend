package com.example.gungjeonjegwa.domain.user.controller

import com.example.gungjeonjegwa.domain.user.data.response.MyProfileResponse
import com.example.gungjeonjegwa.domain.user.data.response.PrivateResponse
import com.example.gungjeonjegwa.domain.user.service.ProfileService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users/profile")
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping("/my")
    fun myProfile(): MyProfileResponse {
        return profileService.myProfileInfo()
    }

    @GetMapping("/private")
    fun myPrivateInfo(): PrivateResponse {
        return profileService.getPrivacyInfo()
    }

    @GetMapping("/giftstamp")
    fun giftstamp() {
        return profileService.deleteStamp()
    }
}