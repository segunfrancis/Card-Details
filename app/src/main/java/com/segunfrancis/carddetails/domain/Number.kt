package com.segunfrancis.carddetails.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Number(

    @SerialName("length")
    val length: Int? = null,

    @SerialName("luhn")
    val luhn: Boolean? = null
)
