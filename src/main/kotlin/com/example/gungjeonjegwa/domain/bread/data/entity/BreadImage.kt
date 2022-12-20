package com.example.gungjeonjegwa.domain.bread.data.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class BreadImage(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val id: Long,

   val imageUrl: String,

   @ManyToOne
   @JoinColumn(name = "breaddetail_id")
   val breadDetail: BreadDetail
) {
}