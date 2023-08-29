package com.bera.techconferenceapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bera.techconferenceapp.presentation.events.common.EventListItem
import com.bera.techconferenceapp.presentation.events.live.LiveEvents
import com.bera.techconferenceapp.presentation.events.past.PastEvents
import com.bera.techconferenceapp.presentation.events.upcoming.UpcomingEvents
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(navController: NavController, isDarkMode: Boolean, onModeToggle: () -> Unit) {

    val viewModel = getViewModel<HomeViewModel>()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {

            val (height, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }
            val focusManager = LocalFocusManager.current
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.searchText.value,
                    onValueChange = { (viewModel::onSearchTextChange)(it) },
                    placeholder = { Text(text = "Search conferences") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    modifier = Modifier
                        .requiredWidth(width-100.dp)
                        .padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    maxLines = 1
                )
                Box(modifier = Modifier
                    .padding(10.dp),
                    Alignment.Center
                ) {
                        ThemeSwitcher(
                            darkTheme = isDarkMode,
                            size = 40.dp,
                            onClick = onModeToggle
                        )
                }
            }
            if (viewModel.searchText.value != "") {
                LazyColumn {
                    if (viewModel.searchEvents.eventList.isEmpty()) {
                        item {
                            Text(
                                text = "No conferences to show",
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        }
                    } else {
                        items(viewModel.searchEvents.eventList) { eventItem ->
                            EventListItem(
                                navController = navController,
                                eventData = eventItem,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        LiveEvents(navController = navController)
                    }
                    item {
                        UpcomingEvents(navController = navController)
                    }
                    item {
                        PastEvents(navController = navController)
                    }
                }
            }
        }
    }
}