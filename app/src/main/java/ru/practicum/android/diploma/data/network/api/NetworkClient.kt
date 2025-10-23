package ru.practicum.android.diploma.data.network.api

import ru.practicum.android.diploma.data.network.request.Request
import ru.practicum.android.diploma.data.network.response.Response

interface NetworkClient {
    suspend fun doRequest(dto: Request): Response
}
