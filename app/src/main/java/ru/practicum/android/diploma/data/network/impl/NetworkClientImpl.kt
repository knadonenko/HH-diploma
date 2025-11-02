package ru.practicum.android.diploma.data.network.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.network.api.APIService
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.network.consts.ResponseStates.BAD_REQUEST
import ru.practicum.android.diploma.data.network.consts.ResponseStates.INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NOT_FOUND
import ru.practicum.android.diploma.data.network.consts.ResponseStates.NO_INTERNET_CONNECTION
import ru.practicum.android.diploma.data.network.consts.ResponseStates.SUCCESS
import ru.practicum.android.diploma.data.network.consts.ResponseStates.UNAUTHORIZED
import ru.practicum.android.diploma.data.network.request.Request
import ru.practicum.android.diploma.data.network.response.Response
import ru.practicum.android.diploma.util.NetworkProvider
import java.io.IOException

class NetworkClientImpl(
    private val apiService: APIService,
    private val networkProvider: NetworkProvider
) : NetworkClient {

    override suspend fun doRequest(dto: Request): Response {
        if (!networkProvider.isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_CONNECTION }
        }

        return withContext(Dispatchers.IO) {
            try {
                sendRequest(dto)
            } catch (_: IOException) {
                createErrorResponse(NO_INTERNET_CONNECTION)
            } catch (_: Throwable) {
                createErrorResponse(INTERNAL_SERVER_ERROR)
            }
        }
    }

    private suspend fun sendRequest(dto: Request): Response {
        val response = when (dto) {
            is Request.AreasRequest -> apiService.getAreas()
            is Request.IndustriesRequest -> apiService.getIndustries()
            is Request.VacanciesRequest -> apiService.getVacancies(dto.options)
            is Request.VacancyDetailsRequest -> apiService.getVacancy(dto.vacancyId)
        }
        return handleResponse(response)
    }

    private fun handleResponse(retrofitResponse: retrofit2.Response<out Response>): Response {
        return when {
            isSuccessfulResponse(retrofitResponse) -> createSuccessResponse(retrofitResponse.body())
            isUnauthorizedResponse(retrofitResponse) -> createErrorResponse(UNAUTHORIZED)
            isNotFoundResponse(retrofitResponse) -> createErrorResponse(NOT_FOUND)
            isServerErrorResponse(retrofitResponse) -> createErrorResponse(INTERNAL_SERVER_ERROR)
            else -> createErrorResponse(BAD_REQUEST)
        }
    }

    private fun isSuccessfulResponse(response: retrofit2.Response<out Response>): Boolean {
        return response.isSuccessful && response.body() != null
    }

    private fun isUnauthorizedResponse(response: retrofit2.Response<out Response>): Boolean {
        return response.code() == UNAUTHORIZED
    }

    private fun isNotFoundResponse(response: retrofit2.Response<out Response>): Boolean {
        return response.code() == NOT_FOUND
    }

    private fun isServerErrorResponse(response: retrofit2.Response<out Response>): Boolean {
        return response.code() == INTERNAL_SERVER_ERROR
    }

    private fun createSuccessResponse(body: Any?): Response {
        return (body as? Response)?.apply {
            resultCode = SUCCESS
        } ?: createErrorResponse(BAD_REQUEST)
    }

    private fun createErrorResponse(errorCode: Int): Response {
        return Response().apply {
            resultCode = errorCode
        }
    }
}
