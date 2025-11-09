package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.filters.impl.FilterInteractorImpl
import ru.practicum.android.diploma.domain.filtersettings.api.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.filtersettings.impl.FilterSettingsInteractorImpl
import ru.practicum.android.diploma.domain.vacanceis.api.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacanceis.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.vacancydetails.api.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.interactor.VacancyDetailsLinkManagerInteractor
import ru.practicum.android.diploma.domain.vacancydetails.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.vacancydetails.impl.VacancyDetailsLinkManagerInteractorImpl

val interactorModule = module {

    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    factory<FilterSettingsInteractor> {
        FilterSettingsInteractorImpl(get())
    }

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }

    factory<VacancyDetailsLinkManagerInteractor> {
        VacancyDetailsLinkManagerInteractorImpl(androidContext())
    }

    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    factory<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    factory<FilterSettingsInteractor> {
        FilterSettingsInteractorImpl(get())
    }
}
