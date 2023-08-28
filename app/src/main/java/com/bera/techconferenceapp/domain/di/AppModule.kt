package com.bera.techconferenceapp.domain.di

import com.bera.techconferenceapp.data.remote.EventApi
import com.bera.techconferenceapp.data.repository.EventRepositoryImpl
import com.bera.techconferenceapp.domain.repository.EventRepository
import com.bera.techconferenceapp.domain.usecases.GetEventsUseCase
import com.bera.techconferenceapp.presentation.events.common.EventViewModel
import com.bera.techconferenceapp.presentation.events.detail.EventDetailViewModel
import com.bera.techconferenceapp.presentation.home.HomeViewModel
import com.bera.techconferenceapp.utils.Constants.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventApi::class.java)
    }

    single<EventRepository> {
        EventRepositoryImpl(get())
    }

    single {
        GetEventsUseCase(get())
    }

    viewModel {
        EventViewModel(get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        EventDetailViewModel()
    }
}