package ru.practicum.android.diploma.data.network.impl

import ru.practicum.android.diploma.data.network.api.APIService
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.network.request.Request
import ru.practicum.android.diploma.data.network.response.Response

class RetrofitNetworkClient(private val apiService: APIService) : NetworkClient {
    override suspend fun doRequest(dto: Request): Response {
        return Response()
    }
}
