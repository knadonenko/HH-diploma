package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repositories.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.repositories.FilterRepositoryImpl
import ru.practicum.android.diploma.data.repositories.FilterSettingsRepositoryImpl
import ru.practicum.android.diploma.data.repositories.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.repositories.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.repositories.WorkPlacesRepositoryImpl
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.filtersettings.api.repository.FilterSettingsRepository
import ru.practicum.android.diploma.domain.vacanceis.api.repository.VacanciesRepository
import ru.practicum.android.diploma.domain.vacancydetails.api.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.workplaces.api.repository.WorkPlacesRepository

/**
 * Модуль для описания компонентов-репозиториев
 */
val repositoryModule = module {

    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get())
    }

    factory<FilterSettingsRepository> {
        FilterSettingsRepositoryImpl(get(), get())
    }

    factory<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get())
    }

    factory<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get())
    }

    factory<FilterRepository> {
        FilterRepositoryImpl(get(), get())
    }

    factory<WorkPlacesRepository> {
        WorkPlacesRepositoryImpl(get(), get())
    }

    factory<FilterSettingsRepository> {
        FilterSettingsRepositoryImpl(get(), get())
    }
}
