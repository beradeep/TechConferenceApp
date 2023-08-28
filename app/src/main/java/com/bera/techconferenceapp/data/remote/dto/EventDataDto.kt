package com.bera.techconferenceapp.data.remote.dto

import com.bera.techconferenceapp.domain.models.EventItem
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class EventDataDto(
    val banner_image: String,
    val date_time: String,
    val description: String,
    val id: Int,
    val organiser_icon: String,
    val organiser_name: String,
    val title: String,
    val venue_city: String,
    val venue_country: String,
    val venue_name: String
) {
    fun toEventItem(): EventItem {
        return EventItem(
            bannerImage = banner_image,
            dateTime = ZonedDateTime.parse(date_time, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            description = description,
            id = id,
            organiserIcon = organiser_icon,
            organiserName = organiser_name,
            title = title,
            venueCity = venue_city,
            venueCountry = venue_country,
            venueName = venue_name
        )
    }
}