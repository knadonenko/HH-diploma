package ru.practicum.android.diploma.domain.vacanceis.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesResponseState

interface VacanciesInteractor {
    fun searchVacancies(text: String, page: Int, filterSettings: FilterSettings?): Flow<VacanciesResponseState>
}
