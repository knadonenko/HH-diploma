package ru.practicum.android.diploma.domain.filtersettings.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

interface FilterSettingsInteractor {
    fun getFilterSettings(): Flow<FilterSettings?>

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
