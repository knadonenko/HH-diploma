package ru.practicum.android.diploma.presentation.workplaces.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea

@Immutable
sealed class WorkPlacesScreenState {
    data object Default : WorkPlacesScreenState()
    data object Loading : WorkPlacesScreenState()
    data class Content(
        val availableAreas: List<FilterArea>,
        var chosenCountry: FilterArea? = null,
        var chosenArea: FilterArea? = null
    ) : WorkPlacesScreenState()

    data object NotFound : WorkPlacesScreenState()
    data object NoInternetConnection : WorkPlacesScreenState()
    data object InternalServerError : WorkPlacesScreenState()
}
