package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.vacancydetails.api.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetailsResponseState

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter,
    private val favoritesRepositoryImpl: FavoritesRepository
) : VacancyDetailsRepository {
    override fun getVacancyDetails(vacancyId: String, fromDB: Boolean): Flow<VacancyDetailsResponseState> = flow {
        emit(if (fromDB) getFromDB(vacancyId) else getFromService(vacancyId))
    }

    private suspend fun getFromService(vacancyId: String): VacancyDetailsResponseState {
        val response = networkClient.doRequest(
            Request.VacancyDetailsRequest(vacancyId)
        )

        return when (response.resultCode) {
            SUCCESS -> {
                with(response as VacancyDetailsResponse) {
                    val vacancy = vacancyConverter.map(response)
                    VacancyDetailsResponseState.Found(vacancy)
                }
            }

            NOT_FOUND, UNAUTHORIZED, BAD_REQUEST -> {
                VacancyDetailsResponseState.BadRequest
            }

            INTERNAL_SERVER_ERROR -> {
                VacancyDetailsResponseState.InternalServerError
            }

            NO_INTERNET_CONNECTION -> {
                VacancyDetailsResponseState.NoInternetConnection
            }

            else -> VacancyDetailsResponseState.BadRequest
        }
    }

    private suspend fun getFromDB(vacancyId: String): VacancyDetailsResponseState {
        return try {
            val vacancy = favoritesRepositoryImpl.getFavoritesById(vacancyId)

            vacancy.map { v ->
                v?.let {
                    VacancyDetailsResponseState.Found(it)
                } ?: VacancyDetailsResponseState.BadRequest
            }.first()
        } catch (_: Exception) {
            VacancyDetailsResponseState.InternalServerError
        }
    }
}
