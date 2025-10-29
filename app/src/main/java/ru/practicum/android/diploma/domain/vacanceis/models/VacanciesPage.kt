package ru.practicum.android.diploma.domain.vacanceis.models

data class VacanciesPage(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<VacanciesInfo>
)
