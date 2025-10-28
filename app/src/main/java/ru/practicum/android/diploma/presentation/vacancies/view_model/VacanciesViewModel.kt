package ru.practicum.android.diploma.presentation.vacancies.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filtersettings.api.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.filtersettings.models.FilterSettings
import ru.practicum.android.diploma.domain.vacanceis.api.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesInfo
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesPage
import ru.practicum.android.diploma.domain.vacanceis.models.VacanciesResponseState
import ru.practicum.android.diploma.presentation.vacancies.models.VacanciesScreenState

class VacanciesViewModel(
    val vacanciesInteractor: VacanciesInteractor,
    val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {
    private val _screenState = MutableStateFlow<VacanciesScreenState>(VacanciesScreenState.Default)
    val screenState = _screenState.asStateFlow()

    private var _isLastPage: Boolean = false;
    private var _currentPage: Int = 0
    private var _filterSettings: FilterSettings? = null
    private var _currentSearchText = MutableStateFlow("")
    val currentSearchText = _currentSearchText.asStateFlow()

    private var _searchJob: Job? = null
    private var _vacancies = mutableListOf<VacanciesInfo>()

    fun onSearchTextChange(newSearchText: String?) {
        if (_currentSearchText.value == newSearchText)
            return

        _currentSearchText.update { newSearchText ?: "" }

        if (_currentSearchText.value.isEmpty()) {
            _searchJob?.cancel()
            _vacancies.clear()
            return
        }

        searchDebounce()
    }

    private fun searchDebounce() {
        _searchJob?.cancel()

        _searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            loadFirstPage()
        }
    }

    private fun loadFirstPage() {
        _currentPage = 0
        _isLastPage = false
        _vacancies.clear()

        loadNextPage()
    }

    private fun loadNextPage() {
        if (_isLastPage)
            return

        _currentPage++

        _screenState.update { VacanciesScreenState.Loading }

        _searchJob = viewModelScope.launch {
            vacanciesInteractor
                .searchVacancies(_currentSearchText.value, _currentPage, _filterSettings)
                .cancellable()
                .collect { responseState ->
                    handleSearchResult(responseState)
                }
        }
    }

    private fun handleSearchResult(responseState: VacanciesResponseState) {
        when (responseState) {
            is VacanciesResponseState.BadRequest,
            is VacanciesResponseState.InternalServerError -> _screenState.update { VacanciesScreenState.InternalServerError }

            is VacanciesResponseState.NoInternetConnection -> _screenState.update { VacanciesScreenState.NoInternetConnection }
            is VacanciesResponseState.Found -> handleSearchFoundResult(responseState.result)
        }
    }

    private fun handleSearchFoundResult(vacanciesPage: VacanciesPage) {
        _isLastPage = vacanciesPage.page == vacanciesPage.pages

        if (vacanciesPage.vacancies.isEmpty())
            _screenState.update { VacanciesScreenState.NotFound }
        else
            _screenState.update {
                VacanciesScreenState.Found(
                    vacanciesPage.vacancies,
                    _isLastPage,
                    vacanciesPage.found
                )
            }
    }

    fun onClearSearchText() {
        _searchJob?.cancel()
        _currentSearchText.update { "" }
        _vacancies.clear()

        _screenState.update { VacanciesScreenState.Default }
    }

    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
