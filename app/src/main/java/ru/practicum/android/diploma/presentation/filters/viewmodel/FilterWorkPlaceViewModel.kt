package ru.practicum.android.diploma.presentation.filters.viewmodel

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
            _screenState.update {
                WorkPlacesScreenState.Content(oldState.availableAreas, oldState.chosenCountry, area)
            }
        }
    }

    fun cleanLoadedAreas() {
        _screenState.update { WorkPlacesScreenState.Default }
    }

    fun onSaveChoice(chosenArea: FilterArea) {
        _screenState.update { WorkPlacesScreenState.Loading }
        var settings = filterSettingsInteractor.getFilterSettings()
        settings = FilterSettings(
            area = chosenArea.id,
            industry = settings?.industry,
            salary = settings?.salary,
            onlyWithSalary = settings?.onlyWithSalary
        )
        filterSettingsInteractor.saveFilterSettings(settings)
    }
}
