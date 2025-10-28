package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.vacancies.viewmodel.VacanciesViewModel

/**
 * Модуль для описания компонентов-viewmodel
 */
val viewModelModule = module {

    viewModel {
        VacanciesViewModel(get(), get())
    }
}
