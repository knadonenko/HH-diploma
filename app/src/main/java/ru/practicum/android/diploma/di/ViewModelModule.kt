package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.presentation.vacancies.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.presentation.vacancies.viewmodel.VacanciesViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.workplaces.viewmodel.WorkPlacesViewModel

/**
 * Модуль для описания компонентов-viewmodel
 */
val viewModelModule = module {

    viewModel {
        VacanciesViewModel(get(), get())
    }

    viewModel { (vacancyId: String, vacancySource: String) ->
        VacancyDetailsViewModel(vacancyId, vacancySource, get(), get(), get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        WorkPlacesViewModel(get())
    }

    viewModel {
        FilterIndustryViewModel(get(), get())
    }
}
