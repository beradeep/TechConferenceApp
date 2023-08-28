package com.bera.techconferenceapp.data.remote

import com.bera.techconferenceapp.data.remote.dto.EventInfoDto
import retrofit2.http.GET

interface EventApi {

    @GET(value = "api")
    suspend fun getEventData(): EventInfoDto
}