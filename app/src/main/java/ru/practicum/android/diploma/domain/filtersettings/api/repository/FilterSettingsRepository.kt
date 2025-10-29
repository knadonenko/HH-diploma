package ru.practicum.android.diploma.domain.filtersettings.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

interface FilterSettingsRepository {
    fun getFilterSettings(): Flow<FilterSettings?>

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
