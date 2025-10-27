package ru.practicum.android.diploma.domain.vacanceis.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacanceis.api.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacanceis.api.repository.VacanciesRepository
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesResponseState
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesSearchSettings

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun searchVacancies(
        text: String,
        page: Int,
        filterSettings: VacanciesSearchSettings
    ): Flow<VacanciesResponseState> {
        return repository.searchVacancies(text, page, filterSettings)
    }
}
