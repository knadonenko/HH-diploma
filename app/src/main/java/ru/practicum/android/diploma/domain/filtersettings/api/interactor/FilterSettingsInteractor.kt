package ru.practicum.android.diploma.domain.filtersettings.api.interactor

import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

interface FilterSettingsInteractor {
    fun getFilterSettings(): FilterSettings?

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
