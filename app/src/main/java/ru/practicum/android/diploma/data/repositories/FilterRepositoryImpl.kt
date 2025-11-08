package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.FilterConverter
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.network.consts.ResponseStates.BAD_REQUEST
import ru.practicum.android.diploma.data.network.consts.ResponseStates.INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NOT_FOUND
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NO_INTERNET_CONNECTION
import ru.practicum.android.diploma.data.network.consts.ResponseStates.SUCCESS
import ru.practicum.android.diploma.data.network.consts.ResponseStates.UNAUTHORIZED
import ru.practicum.android.diploma.data.network.request.Request
import ru.practicum.android.diploma.data.network.response.IndustriesResponse
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState

class FilterRepositoryImpl(
    private val networkClient: NetworkClient,
    private val filterConverter: FilterConverter
) :
    FilterRepository {
    override fun getIndustries(): Flow<FilterIndustryResponseState> = flow {
        val response = networkClient.doRequest(
            Request.IndustriesRequest()
        )

        when (response.resultCode) {
            SUCCESS -> {
                with(response as IndustriesResponse) {
                    val industries = filterConverter.map(response.results)
                    emit(FilterIndustryResponseState.Found(industries))
                }
            }

            NOT_FOUND, UNAUTHORIZED, BAD_REQUEST -> {
                emit(FilterIndustryResponseState.BadRequest)
            }

            INTERNAL_SERVER_ERROR -> {
                emit(FilterIndustryResponseState.InternalServerError)
            }

            NO_INTERNET_CONNECTION -> {
                emit(FilterIndustryResponseState.NoInternetConnection)
            }
        }
    }
}
