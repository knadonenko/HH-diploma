package ru.practicum.android.diploma.domain.filters.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea

@Immutable
sealed interface FilterWorkPlaceResponseState {
    data object NoInternetConnection : FilterWorkPlaceResponseState
    data object InternalServerError : FilterWorkPlaceResponseState
    data object BadRequest : FilterWorkPlaceResponseState
    data class Content(val result: List<FilterArea>) : FilterWorkPlaceResponseState
}
