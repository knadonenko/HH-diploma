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
        _screenState.update { WorkPlacesScreenState.Loading }
        viewModelScope.launch {
            workPlacesInteractor.getAreas().collect { response ->
                when (response) {
                    is WorkPlacesResponseState.Content -> {
                        if (response.result.isNotEmpty()) {
                            _screenState.update { WorkPlacesScreenState.Content(response.result) }
                        } else {
                            _screenState.update { WorkPlacesScreenState.NotFound }
                        }
                    }

                    is WorkPlacesResponseState.BadRequest,
                    is WorkPlacesResponseState.InternalServerError -> {
                        _screenState.update { WorkPlacesScreenState.InternalServerError }
                    }

                    is WorkPlacesResponseState.NoInternetConnection -> {
                        _screenState.update { WorkPlacesScreenState.NoInternetConnection }
                    }
                }
            }
        }
    }
}
