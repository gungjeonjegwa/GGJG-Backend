package com.example.gungjeonjegwa.domain.bread.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class BreadImage(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val id: Long,

   val imageUrl: String,

   val imageInfoUrl: String,

   @ManyToOne
   @JoinColumn(name = "breaddetail_id")
   @JsonIgnore
   val breadDetail: BreadDetail
) {
}