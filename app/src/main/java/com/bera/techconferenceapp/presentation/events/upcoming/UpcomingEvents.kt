package com.bera.techconferenceapp.presentation.events.upcoming

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bera.techconferenceapp.presentation.events.common.EventListItem
import com.bera.techconferenceapp.presentation.events.common.EventType
import com.bera.techconferenceapp.presentation.events.common.EventViewModel
import com.bera.techconferenceapp.presentation.events.common.SortDropDown
import org.koin.androidx.compose.getViewModel

@Composable
fun UpcomingEvents(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel = getViewModel<EventViewModel>()
    val state = viewModel.upcomingEventState

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(2.8f),
                text = "Upcoming Events",
                style = MaterialTheme.typography.titleLarge
            )
            SortDropDown(
                modifier = Modifier
                    .weight(1f)
                    .height(32.dp),
                viewModel = viewModel,
                state = state,
                eventType = EventType.UPCOMING
            )
        }
    }

    if (state.eventList.isEmpty()) {
        Text(
            text = "No conferences to show",
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(start = 20.dp)
        )
    } else {
        state.eventList.forEach { eventItem ->
            EventListItem(
                navController = navController,
                eventData = eventItem,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}