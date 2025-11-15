package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea

@Immutable
sealed interface WorkPlacesScreenState {
    data object Default : WorkPlacesScreenState
    data object Loading : WorkPlacesScreenState
    data class Content(
        val areas: List<FilterArea>,
        val countries: List<FilterArea>,
        val chosenCountry: FilterArea? = null,
        val chosenArea: FilterArea? = null
    ) : WorkPlacesScreenState

    data object NotFound : WorkPlacesScreenState
    data object NoInternetConnection : WorkPlacesScreenState
    data object InternalServerError : WorkPlacesScreenState
}
