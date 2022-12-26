package com.example.gungjeonjegwa.domain.bread.data.dto

import com.example.gungjeonjegwa.domain.delivery.data.entity.SellDeliveryType

class BreadDetailQueryDto(
    val id: Long,

    val title: String,

    val content: String,

    val price: Long?,

    val deliveryPrice: Long,

    val isSoldOut: Boolean,

    val size: Long?,

    val storage: String, // 보관방법

    val expirationDate: String, // 유통기한

    val previewUrl: String, // 미리보기 이미지

    val precaution: String, // 주의사항

    val deliveryNotice: String, // 배송사항 당일배송불가 사항 작성

    val allergy: String, // 알레르기

    val ingredient: String, // 성분표시

    val isLikeItem: Boolean,

    val breadSize: MutableList<BreadSizeDto>,

    val breadImage: MutableList<BreadImageUrlDto>,

    val breadImageInfo: MutableList<BreadImageUrlDto>,

    val sellDeliveryType: MutableList<SellDeliveryType>,
) {
}