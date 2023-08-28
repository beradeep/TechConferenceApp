package com.bera.techconferenceapp.presentation.events.common

import com.bera.techconferenceapp.domain.models.EventItem

data class EventListState(
    val eventList: List<EventItem> = emptyList(),
    val loadError: String? = null,
    val loading: Boolean = false,
    val sortBy: SortBy = SortBy.LATEST,
    val isDropDownExpanded: Boolean = false
)
