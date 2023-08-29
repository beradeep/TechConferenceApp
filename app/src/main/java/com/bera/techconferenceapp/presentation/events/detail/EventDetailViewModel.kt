package com.bera.techconferenceapp.presentation.events.detail

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.lifecycle.ViewModel
import com.bera.techconferenceapp.domain.models.EventItem
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class EventDetailViewModel : ViewModel() {
    fun createMapIntent(eventItem: EventItem): Intent {
        val mapIntent = Intent(Intent.ACTION_VIEW)
        val address = "${eventItem.venueName}, ${eventItem.venueCity}, ${eventItem.venueCountry}"
        val gmmIntentUri =
            Uri.parse("geo:0,0?q=$address")
        mapIntent.data = gmmIntentUri
        mapIntent.setPackage("com.google.android.apps.maps")
        return mapIntent
    }

    fun createCalenderIntent(
        eventItem: EventItem
    ): Intent {

        val calenderIntent  = Intent(Intent.ACTION_INSERT)
        val startMillis = eventItem.dateTime.toEpochSecond() * 1000
        val endMillis = eventItem.dateTime.plusHours(24).toEpochSecond() * 1000

        calenderIntent
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            .putExtra(CalendarContract.Events.TITLE, eventItem.title)
            .putExtra(CalendarContract.Events.DESCRIPTION, eventItem.description)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, eventItem.venueName)
            .putExtra(CalendarContract.Events.ALL_DAY, true)
        return calenderIntent
    }

    fun daysAndHoursLeft(dateTime: ZonedDateTime): Pair<Long, Long> {
        val timeLeft = Duration.between(ZonedDateTime.now(), dateTime)
        val daysLeft = timeLeft.toDays()
        val hoursLeft = timeLeft.toHours()
        return Pair(daysLeft, hoursLeft)
    }

    fun formattedDate(dateTime: ZonedDateTime): String {
        val formatter =
            DateTimeFormatter.ofPattern("MMM dd, uuuu hh:mm:ss a", Locale.ENGLISH)
        val aDate = dateTime.toOffsetDateTime().toString()
        return OffsetDateTime.parse(aDate).atZoneSameInstant(ZoneId.systemDefault())
                .format(formatter)
    }
}