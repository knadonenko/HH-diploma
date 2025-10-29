package ru.practicum.android.diploma.domain.vacanceis.models

import androidx.compose.runtime.Immutable

@Immutable
sealed interface VacanciesResponseState {
    data object NoInternetConnection : VacanciesResponseState
    data object InternalServerError : VacanciesResponseState
    data object BadRequest : VacanciesResponseState
    data class Found(val result: VacanciesPage) : VacanciesResponseState
}
