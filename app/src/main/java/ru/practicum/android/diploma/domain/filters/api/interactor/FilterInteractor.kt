package ru.practicum.android.diploma.domain.filters.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState
import ru.practicum.android.diploma.domain.filters.models.FilterWorkPlaceResponseState

interface FilterInteractor {
    fun getIndustries(): Flow<FilterIndustryResponseState>

    fun getCountries(): Flow<FilterWorkPlaceResponseState>

    fun getAreas(countryId: Int?): Flow<FilterWorkPlaceResponseState>
}
