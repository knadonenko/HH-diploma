package ru.practicum.android.diploma.domain.workplaces.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea

@Immutable
sealed interface WorkPlacesResponseState {
    data object NoInternetConnection : WorkPlacesResponseState
    data object InternalServerError : WorkPlacesResponseState
    data object BadRequest : WorkPlacesResponseState
    data class Found(val result: List<FilterArea>) : WorkPlacesResponseState
}
