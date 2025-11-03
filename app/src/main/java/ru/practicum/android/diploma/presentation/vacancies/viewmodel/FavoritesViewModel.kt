package ru.practicum.android.diploma.presentation.vacancies.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.presentation.vacancies.models.FavoritesScreenState

class FavoritesViewModel : ViewModel() {
    private val _screenState = MutableStateFlow<FavoritesScreenState>(FavoritesScreenState.Default)
    val screenState = _screenState.asStateFlow()
}
