package ru.practicum.android.diploma.data.network.api

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.network.response.AreasResponse
import ru.practicum.android.diploma.data.network.response.IndustriesResponse
import ru.practicum.android.diploma.data.network.response.VacanciesResponse

interface APIService {
    @GET("/areas")
    suspend fun getAreas(): AreasResponse

    @GET("/industries")
    suspend fun getIndustries(): IndustriesResponse

    @GET("/vacancies")
    suspend fun getVacancies(@QueryMap options: Map<String, String>): VacanciesResponse
}
