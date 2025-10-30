package ru.practicum.android.diploma.data.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.network.response.AreasResponse
import ru.practicum.android.diploma.data.network.response.IndustriesResponse
import ru.practicum.android.diploma.data.network.response.VacanciesResponse
import ru.practicum.android.diploma.data.network.response.VacancyDetailsResponse

interface APIService {
    @GET("/areas")
    suspend fun getAreas(): Response<AreasResponse>

    @GET("/industries")
    suspend fun getIndustries(): Response<IndustriesResponse>

    @GET("/vacancies")
    suspend fun getVacancies(@QueryMap options: Map<String, String>): Response<VacanciesResponse>

    @GET("vacancies/{id}")
    suspend fun getVacancy(@Path("id") vacancyId: String): Response<VacancyDetailsResponse>
}
