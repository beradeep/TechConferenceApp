package com.bera.techconferenceapp.presentation.events.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.techconferenceapp.domain.usecases.GetEventsUseCase
import com.bera.techconferenceapp.presentation.events.common.EventType.LIVE
import com.bera.techconferenceapp.presentation.events.common.EventType.PAST
import com.bera.techconferenceapp.presentation.events.common.EventType.UPCOMING
import com.bera.techconferenceapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class EventViewModel(
    private val getAllEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private var allEventState by mutableStateOf(EventListState())

    var liveEventState by mutableStateOf(EventListState())
        private set

    var upcomingEventState by mutableStateOf(EventListState())
        private set

    var pastEventState by mutableStateOf(EventListState())
        private set

    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        getAllEventsUseCase().onEach { resource ->
            withContext(Dispatchers.Default) {
                when (resource) {
                    is Resource.Error -> {
                        allEventState = allEventState.copy(
                            eventList = resource.data ?: emptyList(),
                            loadError = resource.message,
                            loading = false
                        )
                    }

                    is Resource.Loading -> {
                        allEventState = allEventState.copy(
                            eventList = resource.data ?: emptyList(),
                            loading = true
                        )
                    }

                    is Resource.Success -> {
                        allEventState = allEventState.copy(
                            eventList = resource.data ?: emptyList(),
                            loading = false
                        )

                        val currentDateTime = ZonedDateTime.now()

                        liveEventState = allEventState.copy(
                            eventList = allEventState.eventList.filter { eventItem ->
                                eventItem.dateTime.isAfter(currentDateTime) &&
                                        eventItem.dateTime.isBefore(currentDateTime.plusDays(1))
                            }
                        )
                        upcomingEventState = allEventState.copy(
                            eventList = allEventState.eventList.filter { eventItem ->
                                eventItem.dateTime.isAfter(currentDateTime.plusDays(1))
                            }
                        )
                        pastEventState = allEventState.copy(
                            eventList = allEventState.eventList.filter { eventItem ->
                                eventItem.dateTime.isBefore(currentDateTime)
                            }
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onDropDownClick(isExpanded: Boolean, eventType: EventType) {
        when (eventType) {
            LIVE -> {
                liveEventState = liveEventState.copy(isDropDownExpanded = isExpanded)
            }

            UPCOMING -> {
                upcomingEventState = upcomingEventState.copy(isDropDownExpanded = isExpanded)
            }

            PAST -> {
                pastEventState = pastEventState.copy(isDropDownExpanded = isExpanded)
            }
        }
    }

    fun onSortByChange(sortBy: SortBy, eventType: EventType) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                when (eventType) {
                    LIVE -> {
                        if (sortBy != liveEventState.sortBy) {
                            when (sortBy) {
                                SortBy.NEAREST -> {}
                                SortBy.LATEST -> {
                                    liveEventState =
                                        liveEventState.copy(eventList = liveEventState.eventList.sortedBy { it.dateTime })
                                }

                                SortBy.EARLIEST -> {
                                    liveEventState =
                                        liveEventState.copy(eventList = liveEventState.eventList.sortedByDescending { it.dateTime })
                                }

                                SortBy.NAME -> {
                                    liveEventState =
                                        liveEventState.copy(eventList = liveEventState.eventList.sortedBy { it.description })
                                }
                            }
                        }
                        liveEventState = liveEventState.copy(sortBy = sortBy)
                    }

                    UPCOMING -> {
                        if (sortBy != upcomingEventState.sortBy) {
                            when (sortBy) {
                                SortBy.NEAREST -> {}
                                SortBy.LATEST -> {
                                    upcomingEventState =
                                        upcomingEventState.copy(eventList = upcomingEventState.eventList.sortedByDescending { it.dateTime })
                                }

                                SortBy.EARLIEST -> {
                                    upcomingEventState =
                                        upcomingEventState.copy(eventList = upcomingEventState.eventList.sortedBy { it.dateTime })
                                }

                                SortBy.NAME -> {
                                    upcomingEventState =
                                        upcomingEventState.copy(eventList = upcomingEventState.eventList.sortedBy { it.description })
                                }
                            }
                        }
                        upcomingEventState = upcomingEventState.copy(sortBy = sortBy)
                    }

                    PAST -> {
                        if (sortBy != pastEventState.sortBy) {
                            when (sortBy) {
                                SortBy.NEAREST -> {}
                                SortBy.LATEST -> {
                                    pastEventState =
                                        pastEventState.copy(eventList = pastEventState.eventList.sortedBy { it.dateTime })
                                }

                                SortBy.EARLIEST -> {
                                    pastEventState =
                                        pastEventState.copy(eventList = pastEventState.eventList.sortedByDescending { it.dateTime })
                                }

                                SortBy.NAME -> {
                                    pastEventState =
                                        pastEventState.copy(eventList = pastEventState.eventList.sortedBy { it.description })
                                }
                            }
                        }
                        pastEventState = pastEventState.copy(sortBy = sortBy)
                    }
                }
            }
        }
    }
}