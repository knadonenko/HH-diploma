package ru.practicum.android.diploma.domain.vacancydetails.models

interface MarkFavouriteResponseState {
    data object IsSuccess : MarkFavouriteResponseState
    data object IsFailure : MarkFavouriteResponseState
}
