package ru.practicum.android.diploma.presentation.vacancies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.presentation.vacancies.models.FavoritesScreenState

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {
    private val _screenState = MutableStateFlow<FavoritesScreenState>(FavoritesScreenState.Default)
    val screenState = _screenState.asStateFlow()

    fun getFavorites() {
        _screenState.update { FavoritesScreenState.Loading }
        viewModelScope.launch {
            favoritesInteractor.getFavorites().collect { result ->
                if (result.isNotEmpty()) {
                    val data = result.map { vacancy ->
                        VacanciesInfo(
                            id = vacancy.id,
                            name = vacancy.name ?: "",
                            city = vacancy.address?.city ?: "",
                            employerName = vacancy.employer?.name ?: "",
                            employerLogo = vacancy.employer?.logo,
                            salaryFrom = vacancy.salary?.from,
                            salaryTo = vacancy.salary?.to,
                            salaryCurrencySymbol = vacancy.salary?.currency
                        )
                    }
                    _screenState.update { FavoritesScreenState.Content(data) }
                } else {
                    _screenState.update { FavoritesScreenState.Default }
                }
            }
        }
    }
}
