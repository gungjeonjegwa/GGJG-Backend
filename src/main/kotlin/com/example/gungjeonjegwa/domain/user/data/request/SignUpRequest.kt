package com.example.gungjeonjegwa.domain.user.data.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class SignUpRequest(
    @field:NotBlank(message = "아이디는 필수 입력 값입니다.")
    @field:NotNull
    var id: String,

    @field:NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @field:NotNull
    @field:Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,30}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 30자의 비밀번호여야 합니다.")
    var password: String,

    @field:NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @field:NotNull
    var name: String,

    @field:NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @field:NotNull
    var phone: String,

    @field:NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @field:NotNull
    @field:Email(message = "이메일 형식에 맞지 않습니다.")
    var email: String
) {
}