package ru.practicum.android.diploma.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.filtersettings.api.repository.FilterSettingsRepository
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

class FilterSettingsRepositoryImpl(
    private val gson: Gson,
    private val sharedPrefs: SharedPreferences
) : FilterSettingsRepository {
    override fun getFilterSettings(): Flow<FilterSettings?> = flow {
        val jsonString = sharedPrefs.getString(FILTER_SETTINGS_KEY, null)
        val result = jsonString?.let {
            runCatching {
                gson.fromJson(it, FilterSettings::class.java)
            }.getOrNull()
        }
        emit(result)
    }

    override fun saveFilterSettings(filterSettings: FilterSettings) {
        val jsonString = gson.toJson(filterSettings)
        sharedPrefs.edit { putString(FILTER_SETTINGS_KEY, jsonString) }
    }

    override fun clearFilterSettings() {
        sharedPrefs.edit { remove(FILTER_SETTINGS_KEY) }
    }

    companion object {
        private const val FILTER_SETTINGS_KEY = "filter_settings_key"
    }
}
