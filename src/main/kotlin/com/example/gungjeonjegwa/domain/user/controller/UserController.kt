package com.example.gungjeonjegwa.domain.user.controller

import com.example.gungjeonjegwa.domain.user.data.request.DeviceRequest
import com.example.gungjeonjegwa.domain.user.data.request.SignInRequest
import com.example.gungjeonjegwa.domain.user.data.request.SignUpRequest
import com.example.gungjeonjegwa.domain.user.data.response.DuplicatedResponse
import com.example.gungjeonjegwa.domain.user.data.response.SignInResponse
import com.example.gungjeonjegwa.domain.user.data.response.UserTokenResponseDto
import com.example.gungjeonjegwa.domain.user.service.DeviceService
import com.example.gungjeonjegwa.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService,
    val deviceService: DeviceService
) {
    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody request: SignUpRequest): ResponseEntity<*> {
        return ResponseEntity(userService.signUp(request), HttpStatus.CREATED)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity(userService.signIn(request), HttpStatus.OK)
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestHeader refreshToken: String): UserTokenResponseDto {
        return userService.refreshTokenExecute(refreshToken)
    }

    @PostMapping("/signout")
    fun signOut() {
        return userService.signOut()
    }

    @GetMapping("/idcheck")
    fun checkId(@RequestParam id: String): DuplicatedResponse {
        return userService.checkId(id)
    }

    @GetMapping("/emailcheck")
    fun checkEmail(@RequestParam email: String): DuplicatedResponse {
        return userService.checkEmail(email)
    }

    @PostMapping("/device")
    fun createDeviceToken(@RequestBody deviceTokenRequest: DeviceRequest): ResponseEntity<Void> =
        deviceService.createDeviceToken(deviceTokenRequest.deviceToken)
            .let { ResponseEntity.ok().build() }
}
