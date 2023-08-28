package com.bera.techconferenceapp.data.repository

import com.bera.techconferenceapp.data.remote.EventApi
import com.bera.techconferenceapp.domain.models.EventItem
import com.bera.techconferenceapp.domain.repository.EventRepository
import com.bera.techconferenceapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class EventRepositoryImpl(
    private val api: EventApi
): EventRepository {

    private var memoryCache: List<EventItem> = emptyList()

    override fun getAllEvents(): Flow<Resource<List<EventItem>>> = flow {

        if(memoryCache.isNotEmpty()) {
            emit(Resource.Success(memoryCache))
        } else {
            emit(Resource.Loading())

            try {
                val response = api.getEventData()
                memoryCache = response.contentDto!!.data.map {
                    it.toEventItem()
                }

            } catch (e: HttpException) {
                emit(Resource.Error(
                    message = "Oops, something went wrong!",
                    data = memoryCache
                ))

            } catch (e: IOException) {
                emit(Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = memoryCache
                ))
            }

            emit(Resource.Success(memoryCache))
        }
    }.flowOn(Dispatchers.IO)
}