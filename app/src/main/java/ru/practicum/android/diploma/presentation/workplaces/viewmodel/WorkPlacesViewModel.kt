package ru.practicum.android.diploma.presentation.workplaces.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.workplaces.api.interactor.WorkPlacesInteractor
import ru.practicum.android.diploma.domain.workplaces.models.WorkPlacesResponseState
import ru.practicum.android.diploma.presentation.workplaces.models.WorkPlacesScreenState

class WorkPlacesViewModel(private val workPlacesInteractor: WorkPlacesInteractor) : ViewModel() {
    private val _screenState =
        MutableStateFlow<WorkPlacesScreenState>(WorkPlacesScreenState.Default)
    val screenState = _screenState.asStateFlow()

    fun getAreas() {
        viewModelScope.launch {
            workPlacesInteractor.getAreas().collect { response ->
                when (response) {
                    is WorkPlacesResponseState.Found -> {
                        _screenState.update { WorkPlacesScreenState.Found(response.result) }
                    }

                    is WorkPlacesResponseState.BadRequest -> {}
                    is WorkPlacesResponseState.InternalServerError -> {}
                    is WorkPlacesResponseState.NoInternetConnection -> {}
                }
            }
        }
    }
}
