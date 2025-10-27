package ru.practicum.android.diploma.domain.vacanceis.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesResponseState
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesSearchSettings

interface VacanciesInteractor {
    fun searchVacancies(text: String, page: Int, filterSettings: VacanciesSearchSettings): Flow<VacanciesResponseState>
}
