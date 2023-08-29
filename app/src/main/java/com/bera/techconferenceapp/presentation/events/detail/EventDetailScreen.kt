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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bera.techconferenceapp.domain.models.EventItem
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId


@Composable
fun EventDetailScreen(
    eventItem: EventItem, viewModel: EventDetailViewModel = koinViewModel()
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize()
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
                    if (imageState.value) AsyncImage(
                        modifier = Modifier.size(40.dp),
                        model = eventItem.organiserIcon,
                        onError = { imageState.value = false },
                        contentDescription = "Icon"
                    )
                    else Image(
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
                Row {
                    Text(
                        text = "Venue:   ", style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${eventItem.venueName},\n${eventItem.venueCity},\n${eventItem.venueCountry}.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        context.startActivity((viewModel::createMapIntent)(eventItem))
                    }) {
                    Text(text = "OPEN IN MAP", style = MaterialTheme.typography.labelLarge)
                }
                Spacer(modifier = Modifier.height(12.dp))


                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "Date & Time:   ", style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${(viewModel::formattedDate)(eventItem.dateTime)}\n${ZoneId.systemDefault()} Time",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        context.startActivity((viewModel::createCalenderIntent)(eventItem))
                    }) {
                    Text(text = "ADD TO CALENDER", style = MaterialTheme.typography.labelLarge)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = eventItem.description, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                val daysLeft = (viewModel::daysAndHoursLeft)(eventItem.dateTime).first
                val hoursLeft = (viewModel::daysAndHoursLeft)(eventItem.dateTime).second
                if (daysLeft >= 1) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "$daysLeft days to go !",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                } else if (daysLeft >= 0 && hoursLeft >= 0) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "$hoursLeft hours to go !",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}