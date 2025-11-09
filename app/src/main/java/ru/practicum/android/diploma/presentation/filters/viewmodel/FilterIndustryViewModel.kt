package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.filters.models.FilterIndustry
import ru.practicum.android.diploma.domain.filters.models.FilterIndustryResponseState
import ru.practicum.android.diploma.domain.filtersettings.api.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryScreenState

class FilterIndustryViewModel(
    private val filterInteractor: FilterInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {
    private val _screenState = MutableStateFlow<FilterIndustryScreenState>(FilterIndustryScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private var _currentSearchText = MutableStateFlow("")
    val currentSearchText = _currentSearchText.asStateFlow()

    private var _selectedId = MutableStateFlow(SELECTED_INDUSTRY_EMPTY_KEY)
    val selectedId = _selectedId.asStateFlow()

    private var _isSelected = MutableStateFlow(false)
    val isSelected = _isSelected.asStateFlow()

    private var _filterSettings: FilterSettings? = null
    private var _searchJob: Job? = null
    private var _industries = listOf<FilterIndustry>()

    init {
        _screenState.update { FilterIndustryScreenState.Loading }
        _filterSettings = filterSettingsInteractor.getFilterSettings()
        viewModelScope.launch {
            filterInteractor
                .getIndustries()
                .collect { responseState ->
                    handleResult(responseState)
                }
        }
    }

    private fun handleResult(responseState: FilterIndustryResponseState) {
        when (responseState) {
            is FilterIndustryResponseState.BadRequest,
            is FilterIndustryResponseState.InternalServerError ->
                _screenState.update { FilterIndustryScreenState.Error }

            is FilterIndustryResponseState.NoInternetConnection ->
                _screenState.update { FilterIndustryScreenState.NoInternetConnection }

            is FilterIndustryResponseState.Found -> handleFoundResult(responseState.result, true)
        }
    }

    private fun handleFoundResult(industries: List<FilterIndustry>?, isInit: Boolean = false) {
        _screenState.update {
            industries?.let {
                _filterSettings?.industry?.apply {
                    _selectedId.update { _filterSettings!!.industry!! }
                }

                if (isInit) {
                    _industries = industries
                }

                if (industries.isEmpty()) {
                    FilterIndustryScreenState.NotFound
                } else {
                    FilterIndustryScreenState.Content(industries.toList())
                }
            }
                ?: FilterIndustryScreenState.NotFound
        }
    }

    fun onSelectIndustry(id: Int) {
        _isSelected.update { true }
        _selectedId.update { id }
    }

    fun onSearchTextChange(newSearchText: String?) {
        if (_currentSearchText.value == newSearchText) {
            return
        }

        _currentSearchText.update { newSearchText ?: "" }

        if (_currentSearchText.value.isEmpty()) {
            _screenState.update { FilterIndustryScreenState.Content(_industries.toList()) }
            return
        }

        cancelPreviousSearch()
        searchDebounce()
    }

    private fun cancelPreviousSearch() {
        _searchJob?.cancel()
        _searchJob = null
    }

    private fun searchDebounce() {
        _searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search()
        }
    }

    private fun search() {
        val searchedIndustries = _industries
            .filter { it.name.contains(_currentSearchText.value, ignoreCase = true) }
            .sortedBy { it.name }.toList()

        handleFoundResult(searchedIndustries)
    }

    fun onClearSearchText() {
        onSearchTextChange("")
    }

    fun onSaveChoice() {
        _filterSettings = FilterSettings(
            area = _filterSettings?.area,
            areaName = _filterSettings?.areaName,
            industry = _selectedId.value,
            industryName = _industries.first { it.id == _selectedId.value }.name,
            salary = _filterSettings?.salary,
            onlyWithSalary = _filterSettings?.onlyWithSalary
        )

        filterSettingsInteractor.saveFilterSettings(_filterSettings!!)
    }

    override fun onCleared() {
        super.onCleared()
        cancelPreviousSearch()
    }

    private companion object {
        const val SELECTED_INDUSTRY_EMPTY_KEY = -1
        const val SEARCH_DEBOUNCE_DELAY = 500L
    }
}

