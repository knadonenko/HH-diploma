package ru.practicum.android.diploma.domain.filters.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState

interface FilterRepository {
    fun getIndustries(): Flow<FilterIndustryResponseState>
}
