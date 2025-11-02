package ru.practicum.android.diploma.presentation.vacancies.models

import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo

sealed class FavoritesScreenState {
    data object Default : FavoritesScreenState()
    data object Error: FavoritesScreenState()
    data class Content(val data: List<VacanciesInfo>) :
        FavoritesScreenState()
}
