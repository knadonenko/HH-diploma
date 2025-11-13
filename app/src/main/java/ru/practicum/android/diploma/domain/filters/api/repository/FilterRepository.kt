package ru.practicum.android.diploma.domain.filters.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState
import ru.practicum.android.diploma.domain.filters.models.FilterWorkPlaceResponseState

interface FilterRepository {
    fun getIndustries(): Flow<FilterIndustryResponseState>

    fun getAreas(): Flow<FilterWorkPlaceResponseState>
}
