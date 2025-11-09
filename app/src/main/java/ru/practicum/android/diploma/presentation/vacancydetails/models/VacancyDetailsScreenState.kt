package ru.practicum.android.diploma.presentation.vacancydetails.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy

@Immutable
sealed interface VacancyDetailsScreenState {
    data object Default : VacancyDetailsScreenState
    data object Loading : VacancyDetailsScreenState
    data class Found(val data: Vacancy) : VacancyDetailsScreenState
    data object NotFound : VacancyDetailsScreenState
    data object NoInternetConnection : VacancyDetailsScreenState
    data object InternalServerError : VacancyDetailsScreenState
}
