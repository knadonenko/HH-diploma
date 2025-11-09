package ru.practicum.android.diploma.presentation.vacancies.viewmodel

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
    private val vacanciesInteractor: VacanciesInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {
    private val _screenState = MutableStateFlow<VacanciesScreenState>(VacanciesScreenState.Default)
    val screenState = _screenState.asStateFlow()

    private var _isLastPage: Boolean = false
    private var _currentPage: Int = 0
    private var _filterSettings: FilterSettings? = null
    private var _currentSearchText = MutableStateFlow("")
    val currentSearchText = _currentSearchText.asStateFlow()

    private var _searchJob: Job? = null
    private var _loadNextPageJob: Job? = null
    private var _vacancies = mutableListOf<VacanciesInfo>()
    private var _totalCount: Int = 0

    fun loadFilterSettings() {
        _filterSettings = filterSettingsInteractor.getFilterSettings()
    }

    fun searchWithNewSettings() {
        if (_currentSearchText.value.isNotEmpty()){
            loadFirstPage()
        }
    }

    fun onSearchTextChange(newSearchText: String?) {
        if (_currentSearchText.value == newSearchText) {
            return
        }

        _currentSearchText.update { newSearchText ?: "" }

        cancelPreviousSearch()
        cancelNextPageLoad()

        if (_currentSearchText.value.isEmpty()) {
            _vacancies.clear()
            _screenState.update { VacanciesScreenState.Default }
            return
        }

        searchDebounce()
    }

    private fun searchDebounce() {
        _searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            loadFirstPage()
        }
    }

    private fun loadFirstPage() {
        _currentPage = 1
        _isLastPage = false
        _vacancies.clear()
        _totalCount = 0

        _screenState.update { VacanciesScreenState.Loading }
        _searchJob = viewModelScope.launch {
            vacanciesInteractor
                .searchVacancies(_currentSearchText.value, _currentPage, _filterSettings)
                .cancellable()
                .collect { responseState ->
                    handleSearchResult(responseState, isFirstPage = true)
                }
        }
    }

    fun loadNextPage() {
        if (_isLastPage || _loadNextPageJob?.isActive == true) {
            return
        }

        _currentPage++

        val currentData = _vacancies.toList()
        _screenState.update {
            VacanciesScreenState.Found(
                data = currentData,
                isLastPage = _isLastPage,
                totalCount = _totalCount,
                isNextPageLoading = true
            )
        }

        _loadNextPageJob = viewModelScope.launch {
            vacanciesInteractor
                .searchVacancies(_currentSearchText.value, _currentPage, _filterSettings)
                .cancellable()
                .collect { responseState ->
                    handleSearchResult(responseState, isFirstPage = false)
                }
        }
    }

    private fun handleSearchResult(responseState: VacanciesResponseState, isFirstPage: Boolean) {
        when (responseState) {
            is VacanciesResponseState.BadRequest,
            is VacanciesResponseState.InternalServerError -> {
                if (isFirstPage) {
                    _screenState.update { VacanciesScreenState.InternalServerError }
                } else {
                    _currentPage--
                    updateFoundState()
                }
            }

            is VacanciesResponseState.NoInternetConnection -> {
                if (isFirstPage) {
                    _screenState.update { VacanciesScreenState.NoInternetConnection }
                } else {
                    _currentPage--
                    updateFoundState()
                }
            }

            is VacanciesResponseState.Found -> handleSearchFoundResult(responseState.result, isFirstPage)
        }
    }

    private fun handleSearchFoundResult(vacanciesPage: VacanciesPage, isFirstPage: Boolean) {
        _isLastPage = vacanciesPage.page == vacanciesPage.pages
        _totalCount = vacanciesPage.found

        if (isFirstPage) {
            if (vacanciesPage.vacancies.isEmpty()) {
                _screenState.update { VacanciesScreenState.NotFound }
            } else {
                _vacancies.clear()
                _vacancies.addAll(vacanciesPage.vacancies)
                updateFoundState()
            }
        } else {
            _vacancies.addAll(vacanciesPage.vacancies)
            updateFoundState()
        }
    }

    private fun updateFoundState() {
        _screenState.update {
            VacanciesScreenState.Found(
                data = _vacancies.toList(),
                isLastPage = _isLastPage,
                totalCount = _totalCount,
                isNextPageLoading = false
            )
        }
    }

    fun onClearSearchText() {
        cancelPreviousSearch()
        cancelNextPageLoad()
        _currentSearchText.update { "" }
        _vacancies.clear()
        _currentPage = 0
        _isLastPage = false
        _totalCount = 0

        _screenState.update { VacanciesScreenState.Default }
    }

    private fun cancelPreviousSearch() {
        _searchJob?.cancel()
        _searchJob = null
    }

    private fun cancelNextPageLoad() {
        _loadNextPageJob?.cancel()
        _loadNextPageJob = null
    }

    override fun onCleared() {
        super.onCleared()
        cancelPreviousSearch()
        cancelNextPageLoad()
    }

    private companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
