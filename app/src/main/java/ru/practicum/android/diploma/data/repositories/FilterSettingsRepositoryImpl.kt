package ru.practicum.android.diploma.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.filtersettings.api.repository.FilterSettingsRepository
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

class FilterSettingsRepositoryImpl(
    private val gson: Gson,
    private val sharedPrefs: SharedPreferences
) : FilterSettingsRepository {
    override fun getFilterSettings(): FilterSettings? {
        val jsonString = sharedPrefs.getString(FILTER_SETTINGS_KEY, null)

        return jsonString?.let {
            runCatching {
                gson.fromJson(it, FilterSettings::class.java)
            }.getOrNull()
        }
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
