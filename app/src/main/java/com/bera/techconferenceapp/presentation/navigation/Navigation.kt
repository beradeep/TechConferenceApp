package com.bera.techconferenceapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bera.techconferenceapp.domain.models.EventItem
import com.bera.techconferenceapp.presentation.events.detail.EventDetailScreen
import com.bera.techconferenceapp.presentation.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Routes.EventDetailScreen.route) {
            val eventItem = navController.previousBackStackEntry?.savedStateHandle?.get<EventItem>("eventItem")
            eventItem?.let { EventDetailScreen(eventItem = it) }
        }
    }
}