package ru.practicum.android.diploma.presentation.vacancydetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.vacancydetails.api.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.interactor.VacancyDetailsLinkManagerInteractor
import ru.practicum.android.diploma.domain.vacancydetails.models.MarkFavouriteResponseState
import ru.practicum.android.diploma.domain.vacancydetails.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetailsResponseState
import ru.practicum.android.diploma.presentation.vacancydetails.models.VacancyDetailsScreenState

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val vacancyDetailsLinkManagerInteractor: VacancyDetailsLinkManagerInteractor
) : ViewModel() {
    private val _vacancy: Vacancy? = null
    private var _isFavouriteChangeInProgress: Boolean = false

    private val _screenState = MutableStateFlow<VacancyDetailsScreenState>(VacancyDetailsScreenState.Default)
    val screenState = _screenState.asStateFlow()

    init {
        _screenState.update { VacancyDetailsScreenState.Loading }

        viewModelScope.launch {
            vacancyDetailsInteractor
                .getVacancyDetails(vacancyId)
                .cancellable()
                .collect { responseState ->
                    handleResult(responseState)
                }
        }
    }

    private fun handleResult(responseState: VacancyDetailsResponseState) {
        when (responseState) {
            is VacancyDetailsResponseState.BadRequest,
            is VacancyDetailsResponseState.InternalServerError ->
                _screenState.update { VacancyDetailsScreenState.InternalServerError }

            is VacancyDetailsResponseState.NoInternetConnection ->
                _screenState.update { VacancyDetailsScreenState.NoInternetConnection }

            is VacancyDetailsResponseState.Found -> handleFoundResult(responseState.result)
        }
    }

    private fun handleFoundResult(vacancy: Vacancy?) {
        _screenState.update {
            vacancy?.let { VacancyDetailsScreenState.Found(it) }
                ?: VacancyDetailsScreenState.NotFound
        }
    }

    fun onFavoriteClick() {
        if (_isFavouriteChangeInProgress)
        {
            return
        }

        _vacancy?.run {
            _isFavouriteChangeInProgress = true

            viewModelScope.launch {
                vacancyDetailsInteractor
                    .markFavourite(_vacancy)
                    .cancellable()
                    .collect { responseState ->
                        handleFavouriteResult(responseState)
                    }
            }
        }
    }

    private fun handleFavouriteResult(responseState: MarkFavouriteResponseState){
        _isFavouriteChangeInProgress = false

        if (responseState is MarkFavouriteResponseState.IsSuccess)
        {
            _vacancy?.also { it.isFavorite = !it.isFavorite}
            _vacancy?.apply { _screenState.update { VacancyDetailsScreenState.Found(_vacancy) } }
        }
    }

    fun onShareClick() {
        _vacancy?.url?.let { vacancyDetailsLinkManagerInteractor.shareLink(it) }
    }

    fun onPhoneClick(phone: String) {
        vacancyDetailsLinkManagerInteractor.callPhone(phone)
    }

    fun onEmailClick() {
        _vacancy?.contacts?.email?.let { vacancyDetailsLinkManagerInteractor.shareLink(it) }
    }
}
