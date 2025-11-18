package ru.practicum.android.diploma.domain.filters.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState
import ru.practicum.android.diploma.domain.filters.models.FilterWorkPlaceResponseState

class FilterInteractorImpl(private val filterRepository: FilterRepository) : FilterInteractor {
    override fun getIndustries(): Flow<FilterIndustryResponseState> {
        return filterRepository.getIndustries()
    }

    override fun getCountries(): Flow<FilterWorkPlaceResponseState> {
        return filterRepository.getCountries()
    }

    override fun getAreas(): Flow<FilterWorkPlaceResponseState> {
        return filterRepository.getAreas()
    }
}
