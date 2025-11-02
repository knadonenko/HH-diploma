package ru.practicum.android.diploma.domain.vacancydetails.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancydetails.models.MarkFavouriteResponseState
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetailsResponseState

interface VacancyDetailsInteractor {
    fun getVacancyDetails(vacancyId: String): Flow<VacancyDetailsResponseState>
    fun markFavourite(vacancy: Vacancy): Flow<MarkFavouriteResponseState>
}
