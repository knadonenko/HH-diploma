package ru.practicum.android.diploma.domain.filtersettings.impl

import ru.practicum.android.diploma.domain.filtersettings.api.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.filtersettings.api.repository.FilterSettingsRepository
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

class FilterSettingsInteractorImpl(
    private val filterStorageRepository: FilterSettingsRepository
) : FilterSettingsInteractor {
    override fun getFilterSettings(): FilterSettings? {
        return filterStorageRepository.getFilterSettings()
    }

    override fun saveFilterSettings(filterSettings: FilterSettings) {
        filterStorageRepository.saveFilterSettings(filterSettings)
    }

    override fun clearFilterSettings() {
        filterStorageRepository.clearFilterSettings()
    }
}
