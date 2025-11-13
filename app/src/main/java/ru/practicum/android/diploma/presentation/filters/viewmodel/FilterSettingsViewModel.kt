package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filtersettings.api.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings

class FilterSettingsViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {
    private var _isInit = false

    private var _previousAreaName: String? = null
    private var _previousIndustryName: String? = null
    private var _previousSalary: String? = null
    private var _previousOnlyWithSalary: Boolean = false

    private val _areaName = MutableStateFlow<String?>(null)
    private val _industryName = MutableStateFlow<String?>(null)
    private val _salary = MutableStateFlow<String?>(null)
    private val _onlyWithSalary = MutableStateFlow(false)
    private val _hasSettings = MutableStateFlow(false)
    private val _hasSettingsChange = MutableStateFlow(false)

    val areaName = _areaName.asStateFlow()
    val industryName = _industryName.asStateFlow()
    val salary = _salary.asStateFlow()
    val onlyWithSalary = _onlyWithSalary.asStateFlow()
    val hasSettings = _hasSettings.asStateFlow()
    val hasSettingsChange = _hasSettingsChange.asStateFlow()

    private var _debounceJob: Job? = null
    private var _filterSettings: FilterSettings? = null

    init {
        _isInit = true
    }

    fun loadFilterSettings() {
        _filterSettings = filterSettingsInteractor.getFilterSettings()

        _hasSettings.update { false }

        _filterSettings?.also { settings ->
            _areaName.update { settings.areaName }
            _industryName.update { settings.industryName }
            _salary.update { settings.salary ?: "" }
            _onlyWithSalary.update { settings.onlyWithSalary ?: false }

            if (_isInit) {
                _previousAreaName = settings.areaName
                _previousIndustryName = settings.industryName
                _previousSalary = settings.salary ?: ""
                _previousOnlyWithSalary = settings.onlyWithSalary ?: false
            } else {
                _hasSettingsChange.update { hasSettingsChange() }
            }
        }

        if (_isInit) {
            _isInit = false
        }

        _hasSettings.update { hasSettings() }
    }

    private fun hasSettings(): Boolean {
        return !_areaName.value.isNullOrEmpty() || !_industryName.value.isNullOrEmpty()
            || !_salary.value.isNullOrEmpty() || _onlyWithSalary.value
    }

    private fun hasSettingsChange(): Boolean {
        return _previousAreaName != _areaName.value ||
            _previousIndustryName != _industryName.value ||
            _previousSalary != _salary.value ||
            _previousOnlyWithSalary != _onlyWithSalary.value
    }

    fun onClearArea() {
        _areaName.update { null }
        saveSettings()
    }

    fun onClearIndustry() {
        _industryName.update { null }
        saveSettings()
    }

    fun onChangeSalary(salary: String?) {
        _salary.update { salary }

        _debounceJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            saveSettings()
        }
    }

    fun onChangeOnlyWithSalary(withSalary: Boolean) {
        _onlyWithSalary.update { withSalary }
        saveSettings()
    }

    fun onClearAll() {
        _areaName.update { null }
        _industryName.update { null }
        _salary.update { null }
        _onlyWithSalary.update { false }
        _hasSettings.update { false }

        filterSettingsInteractor.clearFilterSettings()
        _hasSettingsChange.update { hasSettingsChange() }
    }

    private fun saveSettings() {
        if (!hasSettings()) {
            filterSettingsInteractor.clearFilterSettings()
            _hasSettings.update { hasSettings() }
            _hasSettingsChange.update { hasSettingsChange() }

            return
        }

        val area = _filterSettings?.area?.takeIf { !_areaName.value.isNullOrEmpty() }
        val areaName = _filterSettings?.areaName?.takeIf { !_areaName.value.isNullOrEmpty() }

        val industry = _filterSettings?.industry?.takeIf { !_industryName.value.isNullOrEmpty() }
        val industryName = _filterSettings?.industryName?.takeIf { !_industryName.value.isNullOrEmpty() }

        filterSettingsInteractor.saveFilterSettings(
            FilterSettings(
                area,
                areaName,
                industry,
                industryName,
                _salary.value,
                _onlyWithSalary.value
            )
        )

        _hasSettings.update { hasSettings() }
        _hasSettingsChange.update { hasSettingsChange() }
    }

    private companion object {
        const val DEBOUNCE_DELAY = 500L
    }
}
