package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repositories.FilterSettingsRepositoryImpl
import ru.practicum.android.diploma.data.repositories.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.filtersettings.api.repository.FilterSettingsRepository
import ru.practicum.android.diploma.domain.vacanceis.api.repository.VacanciesRepository

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
}
