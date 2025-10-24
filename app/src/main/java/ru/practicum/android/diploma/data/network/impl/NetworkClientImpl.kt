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
import java.io.IOException

class NetworkClientImpl(private val apiService: APIService) : NetworkClient {
    override suspend fun doRequest(dto: Request): Response {
        return withContext(Dispatchers.IO) {
            try {
                val retrofitResponse = when (dto) {
                    is Request.AreasRequest -> apiService.getAreas()
                    is Request.IndustriesRequest -> apiService.getIndustries()
                    is Request.VacanciesRequest -> apiService.getVacancies(dto.options)
                    is Request.VacancyRequest -> apiService.getVacancy(dto.vacancyId)
                }

                val body = retrofitResponse.body()
                val code = retrofitResponse.code()

                when {
                    retrofitResponse.isSuccessful && body != null -> {
                        body.apply { resultCode = SUCCESS }
                    }

                    code == 401 -> {
                        Response().apply { resultCode = UNAUTHORIZED }
                    }

                    code == 404 -> {
                        Response().apply { resultCode = NOT_FOUND }
                    }

                    code in 500..599 -> {
                        Response().apply { resultCode = INTERNAL_SERVER_ERROR }
                    }

                    else -> {
                        Response().apply { resultCode = BAD_REQUEST }
                    }
                }
            } catch (_: IOException) {
                Response().apply { resultCode = NO_INTERNET_CONNECTION }
            } catch (_: Throwable) {
                Response().apply { resultCode = INTERNAL_SERVER_ERROR }
            }
        }
    }
}
