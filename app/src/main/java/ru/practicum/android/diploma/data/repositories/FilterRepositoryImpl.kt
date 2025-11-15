package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.FilterConverter
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.network.consts.ResponseStates.BAD_REQUEST
import ru.practicum.android.diploma.data.network.consts.ResponseStates.INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NOT_FOUND
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NO_INTERNET_CONNECTION
import ru.practicum.android.diploma.data.network.consts.ResponseStates.SUCCESS
import ru.practicum.android.diploma.data.network.consts.ResponseStates.UNAUTHORIZED
import ru.practicum.android.diploma.data.network.request.Request
import ru.practicum.android.diploma.data.network.response.AreasResponse
import ru.practicum.android.diploma.data.network.response.IndustriesResponse
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState
import ru.practicum.android.diploma.domain.filters.models.FilterWorkPlaceResponseState
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea

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

    override fun getCountries(): Flow<FilterWorkPlaceResponseState> = flow {
        val response = networkClient.doRequest(Request.AreasRequest)

        when (response.resultCode) {
            SUCCESS -> {
                val areasResponse = response as AreasResponse
                val areas = areasResponse.results.filter { area ->
                    area.parentId == null
                }.mapNotNull { area ->
                    filterConverter.map(area)
                }

                emit(FilterWorkPlaceResponseState.Content(areas))
            }

            NOT_FOUND, UNAUTHORIZED, BAD_REQUEST -> {
                emit(FilterWorkPlaceResponseState.BadRequest)
            }

            INTERNAL_SERVER_ERROR -> {
                emit(FilterWorkPlaceResponseState.InternalServerError)
            }

            NO_INTERNET_CONNECTION -> {
                emit(FilterWorkPlaceResponseState.NoInternetConnection)
            }
        }
    }

    override fun getAreas(countryId: Int?): Flow<FilterWorkPlaceResponseState> = flow {
        val response = networkClient.doRequest(Request.AreasRequest)

        when (response.resultCode) {
            SUCCESS -> {
                val areasResponse = response as AreasResponse
                val filteredAreas = filterAreas(areasResponse.results, countryId)

                emit(FilterWorkPlaceResponseState.Content(filteredAreas))
            }

            NOT_FOUND, UNAUTHORIZED, BAD_REQUEST -> {
                emit(FilterWorkPlaceResponseState.BadRequest)
            }

            INTERNAL_SERVER_ERROR -> {
                emit(FilterWorkPlaceResponseState.InternalServerError)
            }

            NO_INTERNET_CONNECTION -> {
                emit(FilterWorkPlaceResponseState.NoInternetConnection)
            }
        }
    }

    private fun filterAreas(areas: List<FilterAreaDto>, countryId: Int?): List<FilterArea> {
        val filteredAreas = mutableListOf<FilterAreaDto>()

        if (countryId == null) {
            areas.forEach { area ->
                if (area.areas != null) {
                    filteredAreas.addAll(area.areas)
                }
            }
        } else {
            areas.firstOrNull() { area ->
                area.id == countryId
            }?.also { area ->
                if (area.areas != null) {
                    filteredAreas.addAll(area.areas)
                }
            }
        }

        return filteredAreas.mapNotNull { area ->
            filterConverter.map(area)
        }
    }
}
