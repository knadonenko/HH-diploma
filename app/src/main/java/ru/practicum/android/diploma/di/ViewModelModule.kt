package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.vacancies.view_model.VacanciesViewModel

/**
 * Модуль для описания компонентов-viewmodel
 */
val viewModelModule = module {

    viewModel {
        VacanciesViewModel(get(), get())
    }
}
