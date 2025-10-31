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
import ru.practicum.android.diploma.data.network.response.VacanciesResponse
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings
import ru.practicum.android.diploma.domain.vacanceis.api.repository.VacanciesRepository
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesResponseState

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter
) : VacanciesRepository {
    override fun searchVacancies(
        text: String,
        page: Int,
        filterSettings: FilterSettings?
    ): Flow<VacanciesResponseState> = flow {
        val response = networkClient.doRequest(
            Request.VacanciesRequest(convertSettings(text, page, filterSettings))
        )

        when (response.resultCode) {
            SUCCESS -> {
                with(response as VacanciesResponse) {
                    val vacancyPage = vacancyConverter.map(response)
                    emit(VacanciesResponseState.Found(vacancyPage))
                }
            }

            NOT_FOUND, UNAUTHORIZED, BAD_REQUEST -> {
                emit(VacanciesResponseState.BadRequest)
            }

            INTERNAL_SERVER_ERROR -> {
                emit(VacanciesResponseState.InternalServerError)
            }

            NO_INTERNET_CONNECTION -> {
                emit(VacanciesResponseState.NoInternetConnection)
            }
        }
    }

    private fun convertSettings(text: String, page: Int, settings: FilterSettings?): HashMap<String, String> {
        return HashMap<String, String>().apply {
            put(KEY_TEXT, text)
            put(KEY_PAGE, page.toString())
            settings?.area?.let { put(KEY_AREA, it.toString()) }
            settings?.industry?.let { put(KEY_INDUSTRY, it.toString()) }
            settings?.salary?.let { put(KEY_SALARY, it.toString()) }
            settings?.onlyWithSalary?.let { put(KEY_ONLY_WITH_SALARY, it.toString()) }
        }
    }

    companion object {
        private const val KEY_TEXT = "text"
        private const val KEY_AREA = "area"
        private const val KEY_INDUSTRY = "industry"
        private const val KEY_SALARY = "salary"
        private const val KEY_PAGE = "page"
        private const val KEY_ONLY_WITH_SALARY = "only_with_salary"
    }
}
