package com.bera.techconferenceapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.techconferenceapp.domain.usecases.GetEventsUseCase
import com.bera.techconferenceapp.presentation.events.common.EventListState
import com.bera.techconferenceapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getAllEventsUseCase: GetEventsUseCase,
): ViewModel() {

    var searchEvents by mutableStateOf(EventListState())
        private set

    private var allEvents by mutableStateOf(EventListState())

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    fun onSearchTextChange(text: String) {
        searchEvents = allEvents.copy(
            eventList = allEvents.eventList.filter { it.doesMatchSearchQuery(text) }
        )
        _searchText.value = text
    }

    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        getAllEventsUseCase().onEach { resource ->
            withContext(Dispatchers.Default) {
                when (resource) {
                    is Resource.Error -> {
                        allEvents = allEvents.copy(
                            eventList = resource.data ?: emptyList(),
                            loadError = resource.message,
                            loading = false
                        )
                    }

                    is Resource.Loading -> {
                        allEvents = allEvents.copy(
                            eventList = resource.data ?: emptyList(),
                            loading = true
                        )
                    }

                    is Resource.Success -> {
                        allEvents = allEvents.copy(
                            eventList = resource.data ?: emptyList(),
                            loading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}