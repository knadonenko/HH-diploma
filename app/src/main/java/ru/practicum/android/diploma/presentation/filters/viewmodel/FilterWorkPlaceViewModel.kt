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
    private var _isInit: Boolean = true
    private var _previousAreaId: Int? = null
    private var _filterSettings: FilterSettings? = null

    private var _oldScreenStateContent: WorkPlacesScreenState.Content =
        WorkPlacesScreenState.Content(listOf(), listOf())

    private val _screenState = MutableStateFlow<WorkPlacesScreenState>(WorkPlacesScreenState.Default)
    val screenState = _screenState.asStateFlow()

    private var _hasSettingsChange = MutableStateFlow(false)
    val hasSettingsChange = _hasSettingsChange.asStateFlow()

    private var _filteredAreas = mutableStateOf<List<FilterArea>>(emptyList())
    val filteredItems: List<FilterArea> get() = _filteredAreas.value

    private var _currentSearchText = MutableStateFlow("")
    val currentSearchText = _currentSearchText.asStateFlow()

    fun loadFilterSettings() {
        _screenState.update { WorkPlacesScreenState.Loading }

        if (_isInit) {
            _filterSettings = filterSettingsInteractor.getFilterSettings()

            _filterSettings?.also {
                _previousAreaId = it.area
                _oldScreenStateContent = _oldScreenStateContent.copy(
                    chosenAreaName = it.areaName,
                    chosenCountryName = it.countryName
                )
            }

            _isInit = false
        }

        _screenState.update { _oldScreenStateContent }
    }

    fun loadCountries(withAreas: Boolean = false) {
        _screenState.update { WorkPlacesScreenState.Loading }

        viewModelScope.launch {
            filterInteractor.getCountries().collect { response ->
                when (response) {
                    is FilterWorkPlaceResponseState.Content -> {
                        _oldScreenStateContent = _oldScreenStateContent.copy(countries = response.result)

                        if (response.result.isNotEmpty()) {
                            if (withAreas) {
                                loadAreas()
                            } else {
                                _screenState.update { _oldScreenStateContent }
                            }
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

    fun loadAreasAndCountries() {
        _screenState.update { WorkPlacesScreenState.Loading }

        if (_oldScreenStateContent.countries.isEmpty() && _oldScreenStateContent.chosenCountryName.isNullOrEmpty()) {
            loadCountries(true)
        } else {
            loadAreas()
        }
    }

    private fun loadAreas() {
        viewModelScope.launch {
            filterInteractor.getAreas(_oldScreenStateContent.chosenCountry?.id).collect { response ->
                when (response) {
                    is FilterWorkPlaceResponseState.Content -> {
                        _oldScreenStateContent = _oldScreenStateContent.copy(areas = response.result)
                        _filteredAreas.value = _oldScreenStateContent.areas

                        if (response.result.isNotEmpty()) {
                            _screenState.update { _oldScreenStateContent }
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

    fun chooseCountry(country: FilterArea) {
        _oldScreenStateContent = _oldScreenStateContent.copy(
            chosenCountry = country,
            chosenCountryName = country.name
        )

        if (_oldScreenStateContent.chosenCountry?.id != _oldScreenStateContent.chosenArea?.id) {
            _oldScreenStateContent = _oldScreenStateContent.copy(
                chosenArea = null,
                chosenAreaName = null
            )
        }

        _screenState.update {
            _oldScreenStateContent
        }

        _hasSettingsChange.update {
            hasSettingsChange()
        }
    }

    fun chooseArea(area: FilterArea) {
        var oldCountry = _oldScreenStateContent.chosenCountry
        if (oldCountry == null) {
            oldCountry = _oldScreenStateContent.countries.firstOrNull {
                it.id == area.parentId
            }
        }

        _oldScreenStateContent = _oldScreenStateContent.copy(
            chosenArea = area,
            chosenAreaName = area.name,
            chosenCountry = oldCountry,
            chosenCountryName = oldCountry?.name,
        )

        _screenState.update {
            _oldScreenStateContent
        }

        _hasSettingsChange.update {
            hasSettingsChange()
        }
    }

    fun cleanLoadedAreas() {
        setIsInitial()
        _screenState.update { WorkPlacesScreenState.Default }
    }

    fun clearRegion() {
        _oldScreenStateContent = _oldScreenStateContent.copy(
            chosenArea = null,
            chosenAreaName = null,
        )

        _screenState.update {
            _oldScreenStateContent
        }

        _hasSettingsChange.update {
            hasSettingsChange()
        }
    }

    fun clearCountry() {
        _oldScreenStateContent = _oldScreenStateContent.copy(
            chosenCountry = null,
            chosenCountryName = null,
        )

        _screenState.update {
            _oldScreenStateContent
        }

        _hasSettingsChange.update {
            hasSettingsChange()
        }
    }

    fun onSaveChoice() {
        var settings = filterSettingsInteractor.getFilterSettings()
        val country = _oldScreenStateContent.chosenCountry
        val area = _oldScreenStateContent.chosenArea
        _screenState.update { WorkPlacesScreenState.Loading }
        settings = FilterSettings(
            area = area?.id ?: country?.id,
            areaName = area?.name,
            countryName = country?.name,
            generalAreaName = when {
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

        setIsInitial()
    }

    fun setIsInitial() {
        _isInit = true
        _hasSettingsChange.update { false }
        _oldScreenStateContent = WorkPlacesScreenState.Content(listOf(), listOf())
    }

    fun onSearchTextChange(query: String) {
        if (_currentSearchText.value == query) {
            return
        }

        if (_currentSearchText.value.isEmpty()) {
            _filteredAreas.value = _oldScreenStateContent.areas
        }

        _currentSearchText.update { query }

        _filteredAreas.value = _oldScreenStateContent.areas.filter { area ->
            area.name!!.contains(query, ignoreCase = true)
        }
    }

    fun onClearSearchText() {
        _currentSearchText.update { "" }
        _filteredAreas.value = _oldScreenStateContent.areas
    }

    private fun hasSettingsChange(): Boolean {
        val id = _oldScreenStateContent.chosenArea?.id ?: _oldScreenStateContent.chosenCountry?.id
        return _previousAreaId != id
    }
}
