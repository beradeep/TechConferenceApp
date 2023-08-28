package com.bera.techconferenceapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EventInfoDto(
    @SerializedName("content")
    val contentDto: ContentDto?,
    val status: Boolean
)