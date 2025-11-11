package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.filters.models.FilterWorkPlaceResponseState
import ru.practicum.android.diploma.domain.filtersettings.api.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings
import ru.practicum.android.diploma.domain.vacancydetails.models.FilterArea
import ru.practicum.android.diploma.presentation.filters.models.WorkPlacesScreenState

class FilterWorkPlaceViewModel(
    private val filterInteractor: FilterInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {
    private val _screenState = MutableStateFlow<WorkPlacesScreenState>(WorkPlacesScreenState.Default)
    val screenState = _screenState.asStateFlow()

    private var _currentSearchText = MutableStateFlow("")
    val currentSearchText = _currentSearchText.asStateFlow()

    private val _allAreaItems = mutableStateOf<List<FilterArea>>(emptyList())
    private var _filteredAreas = mutableStateOf<List<FilterArea>>(emptyList())
    val filteredItems: List<FilterArea> get() = _filteredAreas.value

    fun loadAreas() {
        if (_screenState.value !is WorkPlacesScreenState.Content) {
            _screenState.update { WorkPlacesScreenState.Loading }
            viewModelScope.launch {
                filterInteractor.getAreas().collect { response ->
                    when (response) {
                        is FilterWorkPlaceResponseState.Content -> {
                            if (response.result.isNotEmpty()) {
                                _screenState.update { WorkPlacesScreenState.Content(response.result) }
                            } else {
                                _screenState.update { WorkPlacesScreenState.NotFound }
                            }
                        }

                        is FilterWorkPlaceResponseState.BadRequest,
                        is FilterWorkPlaceResponseState.InternalServerError -> {
                            _screenState.update { WorkPlacesScreenState.InternalServerError }
                        }

                        is FilterWorkPlaceResponseState.NoInternetConnection -> {
                            _screenState.update { WorkPlacesScreenState.NoInternetConnection }
                        }
                    }
                }
            }
        }
    }

    fun chooseCountry(country: FilterArea) {
        if (_screenState.value is WorkPlacesScreenState.Content) {
            val oldState = _screenState.value as WorkPlacesScreenState.Content
            _screenState.update {
                WorkPlacesScreenState.Content(oldState.availableAreas, country, null)
            }
        }
    }

    fun chooseArea(area: FilterArea) {
        if (_screenState.value is WorkPlacesScreenState.Content) {
            val oldState = _screenState.value as WorkPlacesScreenState.Content
            var oldCountry = oldState.chosenCountry
            if (oldCountry == null) {
                oldCountry = oldState.availableAreas.firstOrNull {
                    it.id == area.parentId
                }
            }
            _screenState.update {
                WorkPlacesScreenState.Content(oldState.availableAreas, oldCountry, area)
            }
        }
    }

    fun cleanLoadedAreas() {
        _screenState.update { WorkPlacesScreenState.Default }
    }

    fun clearRegion() {
        val oldState = _screenState.value as WorkPlacesScreenState.Content
        _screenState.update {
            WorkPlacesScreenState.Content(oldState.availableAreas, oldState.chosenCountry, null)
        }
    }

    fun clearCountry() {
        val oldState = _screenState.value as WorkPlacesScreenState.Content
        _screenState.update {
            WorkPlacesScreenState.Content(oldState.availableAreas, null, oldState.chosenArea)
        }
    }

    fun onSaveChoice() {
        var settings = filterSettingsInteractor.getFilterSettings()
        val state = _screenState.value as WorkPlacesScreenState.Content
        val country = state.chosenCountry
        val area = state.chosenArea
        _screenState.update { WorkPlacesScreenState.Loading }
        settings = FilterSettings(
            area = area?.id ?: country?.id,
            areaName = when {
                area != null && country != null -> "${country.name}, ${area.name}"
                country != null -> country.name
                else -> ""
            },
            industry = settings?.industry,
            industryName = settings?.industryName,
            salary = settings?.salary,
            onlyWithSalary = settings?.onlyWithSalary
        )
        filterSettingsInteractor.saveFilterSettings(settings)
    }

    fun onSearchTextChange(query: String) {
        if (_currentSearchText.value == query) {
            return
        }

        if (_currentSearchText.value.isEmpty()) {
            loadFilteredAreas()
        }

        _currentSearchText.update { query }

        _filteredAreas.value = _allAreaItems.value.filter { area ->
            area.name!!.contains(query, ignoreCase = true)
        }
    }

    fun onClearSearchText() {
        _currentSearchText.update { "" }
        loadFilteredAreas()
    }

    fun loadFilteredAreas() {
        val state = _screenState.value as WorkPlacesScreenState.Content
        _allAreaItems.value = if (state.chosenCountry == null) {
            state.availableAreas.flatMap { it.areas ?: emptyList() }.toMutableList()
        } else {
            state.availableAreas.first { it == state.chosenCountry }.areas!!.toMutableList()
        }
        _filteredAreas.value = _allAreaItems.value
    }
}
