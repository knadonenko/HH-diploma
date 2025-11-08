package ru.practicum.android.diploma.domain.filters.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState

interface FilterInteractor {
    fun getIndustries(): Flow<FilterIndustryResponseState>
}
