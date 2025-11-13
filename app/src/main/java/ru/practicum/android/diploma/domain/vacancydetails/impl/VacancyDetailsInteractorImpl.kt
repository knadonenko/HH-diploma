package ru.practicum.android.diploma.domain.vacancydetails.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.api.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetailsResponseState

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {
    override fun getVacancyDetails(vacancyId: String, fromDB: Boolean): Flow<VacancyDetailsResponseState> {
        return repository.getVacancyDetails(vacancyId, fromDB)
    }
}
