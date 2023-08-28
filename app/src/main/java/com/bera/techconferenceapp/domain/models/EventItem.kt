package com.bera.techconferenceapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class EventItem (
    val bannerImage: String,
    val dateTime: ZonedDateTime,
    val description: String,
    val id: Int,
    val organiserIcon: String,
    val organiserName: String,
    val title: String,
    val venueCity: String,
    val venueCountry: String,
    val venueName: String
): Parcelable {

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombination = listOf(
            title,
            "${title.substringBefore(" ")} ${title.substringAfter(" ")}",
            "${title.first()} ${title.substringAfter(" ").first()}"
        )

        return matchingCombination.any {
            it.contains(query, ignoreCase = true)
        }
    }
}