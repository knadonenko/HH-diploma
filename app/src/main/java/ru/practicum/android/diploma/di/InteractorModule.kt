package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.filtersettings.api.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.filtersettings.impl.FilterSettingsInteractorImpl
import ru.practicum.android.diploma.domain.vacanceis.api.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacanceis.impl.VacanciesInteractorImpl

val interactorModule = module {

    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    factory<FilterSettingsInteractor> {
        FilterSettingsInteractorImpl(get())
    }
}
