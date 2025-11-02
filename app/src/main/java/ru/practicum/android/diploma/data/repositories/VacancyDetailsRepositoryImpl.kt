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
import ru.practicum.android.diploma.data.network.response.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.vacancydetails.api.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.MarkFavouriteResponseState
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetailsResponseState

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter
) : VacancyDetailsRepository {
    override fun getVacancyDetails(vacancyId: String): Flow<VacancyDetailsResponseState> = flow {
        val response = networkClient.doRequest(
            Request.VacancyDetailsRequest(vacancyId)
        )

        when (response.resultCode) {
            SUCCESS -> {
                with(response as VacancyDetailsResponse) {
                    val vacancy = vacancyConverter.map(response)
                    emit(VacancyDetailsResponseState.Found(vacancy))
                }
            }

            NOT_FOUND, UNAUTHORIZED, BAD_REQUEST -> {
                emit(VacancyDetailsResponseState.BadRequest)
            }

            INTERNAL_SERVER_ERROR -> {
                emit(VacancyDetailsResponseState.InternalServerError)
            }

            NO_INTERNET_CONNECTION -> {
                emit(VacancyDetailsResponseState.NoInternetConnection)
            }
        }
    }

    override fun markFavourite(vacancy: Vacancy): Flow<MarkFavouriteResponseState> {
        TODO("Not yet implemented")
    }
}
