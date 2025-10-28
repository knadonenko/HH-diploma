package ru.practicum.android.diploma.domain.vacanceis.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesResponseState
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

interface VacanciesRepository {
    fun searchVacancies(text: String, page: Int, filterSettings: FilterSettings?): Flow<VacanciesResponseState>
}
