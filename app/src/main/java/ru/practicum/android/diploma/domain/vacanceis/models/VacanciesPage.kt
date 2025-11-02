package ru.practicum.android.diploma.domain.vacanceis.models

import androidx.compose.runtime.Immutable

@Immutable
data class VacanciesPage(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<VacanciesInfo>
)
