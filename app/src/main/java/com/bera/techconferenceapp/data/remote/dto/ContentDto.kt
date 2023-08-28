package com.bera.techconferenceapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ContentDto(
    val data: List<EventDataDto>,
    @SerializedName("meta")
    val metaDto: MetaDto
)