package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.filters.models.FilterIndustry

@Immutable
sealed interface FilterIndustryScreenState {
    data object Loading : FilterIndustryScreenState
    data class Content(val data: List<FilterIndustry>) : FilterIndustryScreenState
    data object NotFound : FilterIndustryScreenState
    data object NoInternetConnection : FilterIndustryScreenState
    data object Error : FilterIndustryScreenState
}
