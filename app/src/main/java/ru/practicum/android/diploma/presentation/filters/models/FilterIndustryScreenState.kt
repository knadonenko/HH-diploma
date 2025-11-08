package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable

@Immutable
sealed interface FilterIndustryScreenState {
    data object Loading : FilterIndustryScreenState
    data object Error : FilterIndustryScreenState
    data class Content(val industries: List<String>) : FilterIndustryScreenState
}
