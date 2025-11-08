package ru.practicum.android.diploma.presentation.vacancies.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo

@Immutable
sealed interface FavoritesScreenState {
    data object Default : FavoritesScreenState
    data object Error : FavoritesScreenState
    data class Content(val data: List<VacanciesInfo>) :
        FavoritesScreenState
}
