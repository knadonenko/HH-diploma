package ru.practicum.android.diploma.domain.filtersettings.api.repository

import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

interface FilterSettingsRepository {
    fun getFilterSettings(): FilterSettings?

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
