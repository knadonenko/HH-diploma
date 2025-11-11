package ru.practicum.android.diploma.presentation.vacancies.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo

@Immutable
sealed interface VacanciesScreenState {
    data object Default : VacanciesScreenState
    data object Loading : VacanciesScreenState
    data class Found(
        val data: List<VacanciesInfo>,
        val isLastPage: Boolean,
        val totalCount: Int,
        val isNextPageLoading: Boolean,
        val toast: Int?
    ) : VacanciesScreenState
    data object NotFound : VacanciesScreenState
    data object NoInternetConnection : VacanciesScreenState
    data object InternalServerError : VacanciesScreenState
}
