package com.bera.techconferenceapp.presentation.events.detail

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel

class EventDetailViewModel: ViewModel() {
    fun createMapIntent(address: String): Intent {
        val gmmIntentUri =
            Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        return mapIntent
    }
}