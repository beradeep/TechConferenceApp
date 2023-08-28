package com.bera.techconferenceapp.domain.repository

import com.bera.techconferenceapp.domain.models.EventItem
import com.bera.techconferenceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getAllEvents(): Flow<Resource<List<EventItem>>>
}