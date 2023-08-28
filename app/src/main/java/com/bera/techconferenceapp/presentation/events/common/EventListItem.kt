package com.bera.techconferenceapp.presentation.events.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bera.techconferenceapp.domain.models.EventItem
import com.bera.techconferenceapp.presentation.navigation.Routes

@Composable
fun EventListItem(
    navController: NavController,
    eventData: EventItem,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "eventItem",
                    value = eventData
                )
                navController.navigate(Routes.EventDetailScreen.route)
            }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val imageState = remember {
            mutableStateOf(true)
        }
        if (imageState.value)
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = eventData.organiserIcon,
                onError = { imageState.value = false },
                contentDescription = "Event Image"
            )
        else
            Image(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Default.Refresh,
                contentDescription = null
            )

        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = eventData.title, style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = "${eventData.venueCity}, ${eventData.venueCountry}",
                    overflow = TextOverflow.Ellipsis
                )
            }

        }

    }
}