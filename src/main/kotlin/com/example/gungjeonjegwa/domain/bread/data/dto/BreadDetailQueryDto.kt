package com.example.gungjeonjegwa.domain.bread.data.dto

import java.time.LocalDateTime

class BreadDetailQueryDto(
    val id: Long,

    val content: String,

    val size: Long,

    val stroage: String, // 보관방법

    val expirationDate: String, // 유통기한

    val previewUrl: String, // 미리보기 이미지

    val precaution: String, // 주의사항

    val deliveryNotice: String, // 배송사항 당일배송불가 사항 작성

    val allergy: String, // 알레르기

    val ingredient: String, // 성분표시

    val breadSize: MutableList<BreadSizeDto>,

    val breadImage: MutableList<BreadImageDto>
) {
}