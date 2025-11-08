package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.network.consts.ResponseStates.BAD_REQUEST
import ru.practicum.android.diploma.data.network.consts.ResponseStates.INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NOT_FOUND
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NO_INTERNET_CONNECTION
import ru.practicum.android.diploma.data.network.consts.ResponseStates.SUCCESS
import ru.practicum.android.diploma.data.network.consts.ResponseStates.UNAUTHORIZED
import ru.practicum.android.diploma.data.network.request.Request
import ru.practicum.android.diploma.data.network.response.AreasResponse
import ru.practicum.android.diploma.domain.workplaces.api.repository.WorkPlacesRepository
import ru.practicum.android.diploma.domain.workplaces.models.WorkPlacesResponseState

class WorkPlacesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: VacancyConverter
) :
    WorkPlacesRepository {
    override fun getAreas(): Flow<WorkPlacesResponseState> = flow {
        val response = networkClient.doRequest(Request.AreasRequest)

        when (response.resultCode) {
            SUCCESS -> {
                val areasResponse = response as AreasResponse
                val areas = areasResponse.results.mapNotNull { area ->
                    converter.convertFilterArea(area)
                }
                emit(WorkPlacesResponseState.Content(areas))
            }

            NOT_FOUND, UNAUTHORIZED, BAD_REQUEST -> {
                emit(WorkPlacesResponseState.BadRequest)
            }

            INTERNAL_SERVER_ERROR -> {
                emit(WorkPlacesResponseState.InternalServerError)
            }

            NO_INTERNET_CONNECTION -> {
                emit(WorkPlacesResponseState.NoInternetConnection)
            }
        }
    }
}
