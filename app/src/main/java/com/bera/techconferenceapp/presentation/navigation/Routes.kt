package com.bera.techconferenceapp.presentation.navigation

sealed class Routes(val route: String){
    object HomeScreen: Routes(route = "home")
    object EventDetailScreen: Routes(route = "event-detail")
}
