package ru.practicum.android.diploma.domain.vacanceis.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings
import ru.practicum.android.diploma.domain.vacanceis.api.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacanceis.api.repository.VacanciesRepository
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesResponseState

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun searchVacancies(
        text: String,
        page: Int,
        filterSettings: FilterSettings?
    ): Flow<VacanciesResponseState> {
        return repository.searchVacancies(text, page, filterSettings)
    }
}
