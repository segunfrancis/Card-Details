package com.segunfrancis.carddetails.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bank(

    @SerialName("phone")
    val phone: String? = null,

    @SerialName("city")
    val city: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("url")
    val url: String? = null
)