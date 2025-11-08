package ru.practicum.android.diploma.domain.filters.models

import androidx.compose.runtime.Immutable

@Immutable
sealed interface FilterIndustryResponseState {
    data object NoInternetConnection : FilterIndustryResponseState
    data object InternalServerError : FilterIndustryResponseState
    data object BadRequest : FilterIndustryResponseState
    data class Found(val result: List<FilterIndustry>) : FilterIndustryResponseState
}
