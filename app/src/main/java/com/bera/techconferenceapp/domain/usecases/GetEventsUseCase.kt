package com.bera.techconferenceapp.domain.usecases

import com.bera.techconferenceapp.domain.models.EventItem
import com.bera.techconferenceapp.domain.repository.EventRepository
import com.bera.techconferenceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetEventsUseCase(
    private val repository: EventRepository
) {

    operator fun invoke(): Flow<Resource<List<EventItem>>> {
        return repository.getAllEvents()
    }
}