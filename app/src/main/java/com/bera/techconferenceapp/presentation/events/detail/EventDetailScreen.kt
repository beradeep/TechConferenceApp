package com.bera.techconferenceapp.presentation.events.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bera.techconferenceapp.domain.models.EventItem
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun EventDetailScreen(
    eventItem: EventItem
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = eventItem.bannerImage,
                contentDescription = "Banner Image",
                contentScale = ContentScale.FillWidth
            )
        }
        item {
            Column(
                Modifier.padding(20.dp)
            ) {

                Text(text = eventItem.title, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val imageState = remember {
                        mutableStateOf(true)
                    }
                    if (imageState.value)
                        AsyncImage(
                            modifier = Modifier.size(40.dp),
                            model = eventItem.organiserIcon,
                            onError = { imageState.value = false },
                            contentDescription = "Icon"
                        )
                    else
                        Image(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = eventItem.organiserName,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "Venue:   ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${eventItem.venueName},\n${eventItem.venueCity},\n${eventItem.venueCountry}.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                val formatter =
                    DateTimeFormatter.ofPattern("MMM dd, uuuu hh:mm:ss a", Locale.ENGLISH)
                val aDate = eventItem.dateTime.toOffsetDateTime().toString()
                val formattedDate = OffsetDateTime.parse(aDate)
                    .atZoneSameInstant(ZoneId.systemDefault())
                    .format(formatter)
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "Date & Time:   ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$formattedDate\n${ZoneId.systemDefault()} Time",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = eventItem.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}