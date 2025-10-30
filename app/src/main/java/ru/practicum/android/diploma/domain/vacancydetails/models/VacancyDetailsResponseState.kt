package ru.practicum.android.diploma.domain.vacancydetails.models

import androidx.compose.runtime.Immutable

@Immutable
sealed interface VacancyDetailsResponseState {
    data object NoInternetConnection : VacancyDetailsResponseState
    data object InternalServerError : VacancyDetailsResponseState
    data object BadRequest : VacancyDetailsResponseState
    data class Found(val result: VacancyDetails) : VacancyDetailsResponseState
}
